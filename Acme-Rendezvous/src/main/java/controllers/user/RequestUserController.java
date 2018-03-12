package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import services.ServicesService;
import services.UserService;
import controllers.AbstractController;
import domain.Rendezvous;
import domain.Request;
import domain.User;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController{

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ServicesService servicesService;
	
	@Autowired
	private UserService userService;
	
	// Creation ---------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView create(@RequestParam final int servicesId) {
			ModelAndView res;
			
//			res = new ModelAndView("redirect:../../");
//			
//			User user = userService.findByPrincipal();
//			
//			Collection<Rendezvous> rendezvous = new ArrayList<Rendezvous>();
//			rendezvous = user.getRendezvous();
//			
//			for(Rendezvous r: rendezvous){
//				if (this.servicesService.findOne(servicesId) == null || !(r.getServices().equals(servicesService.findOne(servicesId))))
//					res = new ModelAndView("redirect:../../");
//				else {
//					final Request request = this.requestService.create();
//					request.setServices(this.servicesService.findOne(servicesId));
//					res = this.createEditModelAndView(request);
//				}
//			}		

			if (this.servicesService.findOne(servicesId) == null)
				res = new ModelAndView("redirect:../../");
			else {
				final Request request = this.requestService.create();
				request.setServices(this.servicesService.findOne(servicesId));
				res = this.createEditModelAndView(request);
			}
			
			return res;
		}
		
		// Saving --------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Request request, final BindingResult binding) {
			ModelAndView res;
			if (binding.hasErrors())
				res = this.createEditModelAndView(request, "request.params.error");
			else
				try {
					this.requestService.save(request);
					res = new ModelAndView("redirect:list.do");
				} catch (final Throwable oops) {
					res = this.createEditModelAndView(request, "request.commit.error");
				}
			return res;
		}
		
		// Ancillary methods --------------------------------------------------

		protected ModelAndView createEditModelAndView(final Request request) {
			ModelAndView result;

			result = this.createEditModelAndView(request, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final Request request, final String message) {
			ModelAndView result;
			
					
			result = new ModelAndView("request/user/edit");
			result.addObject("message", message);
			result.addObject("requestUri", "request/user/edit.do");

			return result;
		}
}
