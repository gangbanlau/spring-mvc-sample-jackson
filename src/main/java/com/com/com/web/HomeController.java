package com.com.com.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.com.com.domain.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/getuser", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public  User getuser(Locale locale, Model model) {
		Date now = new Date();
		User u = new User();
		u.setId(1L);
		u.setName("spring");
		u.setSomeTime(now);
		u.setAnotherTime(now);
		u.setPassword("pwd");
		return u;
	}
	
	@RequestMapping(value = "/createuser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User putuser(@RequestBody @Valid User u, BindingResult rs,
		      HttpServletRequest request) {
		if (u.getOptionalval() == null)
			logger.info("optionalval is null");
		return u;
	}
}
