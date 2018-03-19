package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;

@Controller
@RequestMapping("/request/manager")
public class RequestManagerController extends AbstractController {

	@Autowired
	private RequestService requestService;

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView list(@RequestParam final int rendezvousId) {
//		ModelAndView result;
//
//		Collection<Request> requests = requestService.findRequestByRendezvous(rendezvousId);
//
//		result = new ModelAndView("request/list");
//		result.addObject("request", requests);
//		result.addObject("requestURI", "request/manager/list.do");
//
//		return result;
//	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Request> requests = requestService.findAll();

		result = new ModelAndView("request/list");
		result.addObject("request", requests);
		result.addObject("requestURI", "request/manager/list.do");

		return result;
	}
}
