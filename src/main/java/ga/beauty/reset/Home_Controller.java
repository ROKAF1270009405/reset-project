package ga.beauty.reset;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ga.beauty.reset.services.Event_Service;
import ga.beauty.reset.services.Magazine_Service;
import ga.beauty.reset.utils.LogEnum;

@Controller
public class Home_Controller {
	
	Logger logger = Logger.getLogger(Home_Controller.class);
	@Autowired
	Magazine_Service magazine_service;
	@Autowired
	Event_Service event_service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest req) throws Exception {
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null) ip = req.getRemoteAddr();		
        logger.info(LogEnum.INTER+"메인페이지에 {ip:"+ip+", locale:"+locale+"} 가 들어왔습니다. ");
        magazine_service.main_listPage(model);
        event_service.main_listPage(model);
		return "index";
	}
	
	@RequestMapping(value="/error")
	public String sendError() throws Exception{
		return "error/error";
	}
	
}
