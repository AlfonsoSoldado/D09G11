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

import services.RendezvousService;
import services.RequestService;
import services.UserService;
import controllers.AbstractController;
import domain.Rendezvous;
import domain.Request;
import domain.User;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {
	
	private int rId;

	@Autowired
	private RequestService requestService;

	@Autowired
	private RendezvousService rendezvousService;

	@Autowired
	private UserService userService;
	
	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String message) {
		ModelAndView result;
		Collection<Request> request;
		
		User user;
		user = userService.findByPrincipal();
		int userId = user.getId();

//		request = this.requestService.findAll();
		request = this.requestService.findRequestByUser(userId);

		result = new ModelAndView("request/list");
		result.addObject("request", request);
		result.addObject("requestURI", "request/user/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int rendezvousId) {
		ModelAndView res;

		User user = userService.findByPrincipal();
		
		rId = rendezvousId;
		Rendezvous r = rendezvousService.findOne(rId);

		Collection<Rendezvous> rendezvous = new ArrayList<Rendezvous>();
		rendezvous = user.getRendezvous();

		if (r == null || !(rendezvous.contains(r)))
			res = new ModelAndView("redirect:../../");
		else {
			final Request request = this.requestService.create();
			
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
				this.requestService.save(request, rId);				
//				res = new ModelAndView("redirect:../list.do?rendezvousId=" + rId);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(request,
						"request.commit.error");
			}
		return res;
	}

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Request request,
			final BindingResult binding) {
		ModelAndView res;
		try {
			this.requestService.delete(request);
			res = new ModelAndView("redirect:../../");
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

	protected ModelAndView createEditModelAndView(final Request request,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("request/user/edit");
		result.addObject("request", request);
		result.addObject("message", message);

		return result;
	}
}
