package ga.beauty.reset.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ga.beauty.reset.services.Exp_Service;

@Controller
public class Exp_Controller {
	Logger log=Logger.getLogger(getClass());
	
	@Autowired
	Exp_Service exp_Service;
	
	@RequestMapping(value="/exp",method=RequestMethod.GET)
	public String exp() {
	
		return "ranking_review/expTest";
	}
	
	@RequestMapping(value="/exp", method=RequestMethod.POST)
	public void expUp(@RequestBody Map map,HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
		log.debug("param: "+map.get("email")+"/"+map.get("type"));
		String email=map.get("email").toString();
		String type=map.get("type").toString();
		resp.getWriter().print(exp_Service.up(email,type));
	}
	
	@RequestMapping(value="/exp", method=RequestMethod.DELETE)
	public void expdown(@RequestBody Map map,HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
		log.debug("param: "+map.get("email")+"/"+map.get("type"));
		String email=map.get("email").toString();
		String type=map.get("type").toString();
		resp.getWriter().print(exp_Service.down(email,type));
	}
}