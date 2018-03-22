package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Rendezvous;
import domain.Services;
import services.RendezvousService;
import services.ServicesService;

@Controller
@RequestMapping("/services/administrator")
public class ServicesAdministratorController extends AbstractController {

	@Autowired
	private ServicesService servicesService;
	@Autowired
	private RendezvousService rendezvousService;

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView delete(int servicesId) {
		ModelAndView res;
		Services services = this.servicesService.findOne(servicesId);

		try {
			if (services!=null) {
				services.setCanceled(true);
				Rendezvous rendezvous =services.getRendezvous();
				rendezvous.setServices(null);
				this.rendezvousService.save(rendezvous);
				services.setRendezvous(null);
				
				this.servicesService.save(services);
					
			}
			res = new ModelAndView("redirect:../../");

		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			res = this.createEditModelAndView(services, "services.commit.error");
		}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Services services) {
		ModelAndView result;

		result = this.createEditModelAndView(services, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Services services, final String message) {
		
		ModelAndView result = new ModelAndView("services/list");
		result.addObject("services", services);
		
		result.addObject("message", message);
		result.addObject("requestUri", "services/list.do");

		return result;
	}

}
