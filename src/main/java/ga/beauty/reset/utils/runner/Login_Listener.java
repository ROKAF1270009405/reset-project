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

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ga.beauty.reset.dao.entity.User_Vo;
import ga.beauty.reset.dao.entity.stat.Log_C_Vo;
import ga.beauty.reset.utils.MySDF;

@Component(value="login_Lsn")
public class Login_Listener implements Common_Listener{

	private static final Logger logger = Logger.getLogger(Login_Listener.class);
	// login/yy/MM/dd.json
	//{"data":[{"name":String,"num":int}]}
	private String defaultFP = "c:/reset/report/login/";

	// addLog 할때 user 넣으면 로그인숫자 올리기
	// 접속자는 null 
	// 일마다 로그인 한 사람들 총수
	// XXX 추가작업-월마다 다 더해서 하나 만들기
	
	private List<Log_C_Vo> list;
	private ObjectMapper objectMapper;
	private JsonNode node;	
	
	public Login_Listener() {
		init();
	}
	
	private void init() {
		list = new ArrayList<Log_C_Vo>();
		objectMapper = new ObjectMapper();
		Date date = new Date();
		String filename =  
				defaultFP + MySDF.SDF_Y.format(date)
				+"/"+MySDF.SDF_M.format(date)
				+"/"+MySDF.SDF_D.format(date)
				+".json";
		File file = new File(filename);
		if(file.exists()) {
			try {
				node = objectMapper.readTree(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			list = objectMapper.convertValue(node.findValue("data"), new TypeReference<List<Log_C_Vo>>(){});
		}		
	}// init()

	@Async("threadPoolTaskExecutor")
	@Override
	public <T> Future<String> addLog(T bean, String type, int chNum) throws Exception {
		if(bean == null) {
			changeValue(null,type,chNum);
		}else if(bean instanceof User_Vo) {
			User_Vo target = (User_Vo)bean;
			changeValue(target,type,chNum);
		}
        return new AsyncResult<String>("Success");
	}// addLog()

	
	private void changeValue(User_Vo target, String type, int chNum) {
		Log_C_Vo checkVo = null;
		if(target!=null) checkVo = new Log_C_Vo("로그인",0);
		else checkVo = new Log_C_Vo("접속자",0);
		synchronized(this){
			// 기존에 있는건지 확인
			int maybeIdx = list.indexOf(checkVo);
			if(maybeIdx>=0) checkVo = list.get(maybeIdx);
			else list.add(checkVo);
			// 어떤거 값 변화 
			if(type.equals("num")) { // 사실 안써도 됨.
				checkVo.setNum(checkVo.getNum()+chNum);
			}
		}
	}//changeValue()

	@Override
	public List getList() throws Exception {
		return list;
	}//getList()

	@Override
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
			StringBuilder sbr = createJsonString();
			init();
			try(BufferedWriter buffOut = new BufferedWriter(new FileWriter(file))){
				buffOut.write(sbr.toString());
				logger.info("@저장@ ["+MySDF.SDF_ALL.format(date)+"]일의 로그인/접속자 수치가 저장되었습니다.");
			}
		}		
	}// saveLogOneday()
	
	@Override
	protected void finalize() throws Throwable {
		saveLogOneday();
		super.finalize();
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
		return null;
	}//createJsonString()
	
}// Login_Listener
