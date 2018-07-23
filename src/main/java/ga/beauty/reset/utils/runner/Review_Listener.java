package ga.beauty.reset.utils.runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ga.beauty.reset.dao.entity.Reviews_Vo;
import ga.beauty.reset.dao.entity.stat.Log_C_Vo;
import ga.beauty.reset.utils.LogEnum;
import ga.beauty.reset.utils.MySDF;

@Component("review_Listener")
public class Review_Listener implements Common_Listener {

	Logger logger = Logger.getLogger(Review_Listener.class);
	
	// like/yyyy/MM/dd.json
	//{"data":[{"name":String,"num":int}]}
	private String defaultFP = "c:/reset/report/review/";
	// 좋아요 총량 / 일별 증가량 : DONE

	private List<Log_C_Vo> list;
	private ObjectMapper objectMapper;
	private JsonNode node;
	
	public Review_Listener() throws IOException {
		init();
		logger.info(LogEnum.INIT+"("+getClass()+") 생성완료");
	}
	
	private void init() throws IOException {
		list = new ArrayList<Log_C_Vo>();
		objectMapper = new ObjectMapper();
		Date date = new Date();
		String filename =  
				defaultFP + MySDF.SDF_Y.format(date)
				+"/"+MySDF.SDF_M.format(date)
				+"/"+MySDF.SDF_D.format(date)
				+".json";
		File file = new File(filename);
		if(!file.exists()) {
			new File(file.getParent()).mkdirs();
		}else {
			if(file.length()!=0) {
			try {
				node = objectMapper.readTree(file);
			} catch (IOException e) {
				logger.error(LogEnum.ERROR+":"+e.getMessage());
				e.printStackTrace();
			}
			list = objectMapper.convertValue(node.findValue("data"), new TypeReference<List<Log_C_Vo>>(){});
			}
		}		
	}// init()

	@Async("threadPoolTaskExecutor")
	@Override
	public <T> Future<String> addLog(T bean, String type, int chNum) throws Exception {
		if(bean instanceof Reviews_Vo) {
			Reviews_Vo target = (Reviews_Vo)bean;
			changeValue(target,type,chNum);
		}
        return new AsyncResult<String>("Success");
	}// addLog()
	
	private void changeValue(Reviews_Vo target, String type, int chNum) {
		Log_C_Vo checkVo = new Log_C_Vo("리뷰",0);
		synchronized(this){
			// 기존에 있는건지 확인
			int maybeIdx = list.indexOf(checkVo);
			if(maybeIdx==0) checkVo = list.get(maybeIdx);
			else list.add(checkVo);
			// 어떤거 값 변화 
			if(type.equals("num")) { // 사실 안써도 됨.
				checkVo.setNum(checkVo.getNum()+chNum);
				logger.info(LogEnum.REV+" [NO."+target.getRev_no()+"] 리뷰의 좋아요가 ["+chNum+"]만큼 변하였습니다.");
			}
		}
	}//changeValue()

	@Override
	public List getList() throws Exception {
		return list;
	}//getList()

	@Override
	@Async("threadPoolTaskExecutor")
	@Scheduled(cron=" 0 2 0 * * *\r\n" )
	public void saveLogOneday() throws Exception {
			synchronized (this) {
				Calendar cal = new GregorianCalendar();
				cal.add(Calendar.DATE, -1);
				Date date = cal.getTime();
				String filename =  
						defaultFP + MySDF.SDF_Y.format(date)
						+"/"+MySDF.SDF_M.format(date)
						+"/"+MySDF.SDF_D.format(date)
						+".json";
				File file = new File(filename);
				if(!file.exists()) {
					new File(file.getParent()).mkdirs();
				}
				StringBuilder sbr = createJsonString();
				try(BufferedWriter buffOut = new BufferedWriter(new FileWriter(file))){
					buffOut.write(sbr.toString());
					buffOut.flush();
					logger.info(LogEnum.SAVA_LOG+"["+MySDF.SDF_ALL.format(date)+"]일의 리뷰-좋아요 수치가 저장되었습니다.");
				}
				init();
			}
	}// saveLogOneday()
	
	@Override
	@PreDestroy
	public void saveTmp() throws Exception {
		if(list.size()==0) {return ;}
		synchronized (this) {
			Date date = new Date();
			String filename =  
					defaultFP + MySDF.SDF_Y.format(date)
					+"/"+MySDF.SDF_M.format(date)
					+"/"+MySDF.SDF_D.format(date)
					+".json";
			File file = new File(filename);
			if(!file.exists()) {
				new File(file.getParent()).mkdirs();
			}
			StringBuilder sbr = createJsonString();
			try(BufferedWriter buffOut = new BufferedWriter(new FileWriter(file))){
				buffOut.write(sbr.toString());
				buffOut.flush();
				logger.warn(LogEnum.SAVA_LOG+"["+MySDF.SDF_ALL.format(date)+"]일의 리뷰-좋아요 로그가 임시저장 되었습니다.");
			}
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		saveTmp();
	}// finalize()

	private StringBuilder createJsonString() {
		StringBuilder sbr = new StringBuilder("{\"data\":[");
		Iterator<Log_C_Vo> ite = list.iterator();
		int i=0;
		while(ite.hasNext()) {
			if(i!=0)sbr.append(",");
			sbr.append(ite.next());
			i++;
		}
		sbr.append("]}");
		return sbr;
	}//createJsonString()
	
}//Review_Listener
