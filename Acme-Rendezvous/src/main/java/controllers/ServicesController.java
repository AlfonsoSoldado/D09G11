package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Services;
import services.ServicesService;

@Controller
@RequestMapping("/services")
public class ServicesController  extends AbstractController{

	
	@Autowired
	private ServicesService servicesService;
	
		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
	
		
		Collection<Services> services = servicesService.servicesAviables();

		result = new ModelAndView("services/list");
		result.addObject("services", services);
		result.addObject("requestURI", "services/list.do");

		return result;
	}
}
