package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import controllers.AbstractController;
import domain.Rendezvous;

@Controller
@RequestMapping("/rendezvous/administrator")
public class RendezvousAdministratorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private RendezvousService rendezvousService;

	// Constructors ---------------------------------------------------------

	public RendezvousAdministratorController() {
		super();
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(rendezvousId);
		result = this.createEditModelAndView(rendezvous);

		return result;
	}
	
	// Deleting --------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(@Valid final Rendezvous rendezvous,
				final BindingResult binding) {
			ModelAndView res;
			try {
				this.rendezvousService.delete(rendezvous);
				res = new ModelAndView("redirect:../list.do");
			} catch (final Throwable oops) {
			
				res = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
			}
			return res;
		}
	

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous) {
		ModelAndView result;

		result = this.createEditModelAndView(rendezvous, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Rendezvous rendezvous,
			final String message) {
		ModelAndView result;
		
		result = new ModelAndView("rendezvous/edit");
		result.addObject("rendezvous", rendezvous);
		result.addObject("message", message);
		result.addObject("requestUri", "rendezvous/administrator/edit.do");
		
		return result;
	}
}
