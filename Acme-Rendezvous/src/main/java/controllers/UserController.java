
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import services.UserService;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Constructors ---------------------------------------------------------

	public UserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> user;

		user = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("user", user);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	// Display --------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {
		ModelAndView result;

		final User user = this.userService.findOne(userId);

		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("requestURI", "user/display.do");

		return result;
	}

	@RequestMapping(value = "/displayByRendezvous", method = RequestMethod.GET)
	public ModelAndView displayByRendezvous(@RequestParam final int rendezvousId) {
		ModelAndView result;

		final Rendezvous rdv = this.rendezvousService.findOne(rendezvousId);
		if (rdv.getDeleted() == false) {
			final User user = this.userService.findCreator(rendezvousId);

			result = new ModelAndView("user/display");
			result.addObject("user", user);
			result.addObject("requestURI", "user/display.do");
		} else
			result = new ModelAndView("redirect:../");
		return result;
	}
	
	@RequestMapping(value = "/displayByComment", method = RequestMethod.GET)
	public ModelAndView displayByComment(@RequestParam final int commentId) {
		ModelAndView result;
		
		User user;
		user = userService.findUserByComment(commentId);
		
		result = new ModelAndView("user/displayByComment");
		result.addObject("user", user);
		result.addObject("requestURI", "user/displayByComment.do");
		return result;
	}
	
	@RequestMapping(value = "/displayByAnswer", method = RequestMethod.GET)
	public ModelAndView displayByAnswer(@RequestParam final int answerId) {
		ModelAndView result;
		
		User user;
		user = userService.findUserByAnswer(answerId);
		
		result = new ModelAndView("user/displayByAnswer");
		result.addObject("user", user);
		result.addObject("requestURI", "user/displayByAnswer.do");
		return result;
	}
	
	@RequestMapping(value = "/listCreator", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvousId) {
		ModelAndView result;

		final Rendezvous rdv = this.rendezvousService.findOne(rendezvousId);
		if (rdv.getDeleted() == false) {
			User user;
			user = this.userService.findCreator(rendezvousId);

			result = new ModelAndView("user/listCreator");
			result.addObject("user", user);
			result.addObject("requestURI", "user/listCreator.do");
		} else
			result = new ModelAndView("redirect:../");

		return result;
	}
	@RequestMapping(value = "/listAttendant", method = RequestMethod.GET)
	public ModelAndView listAttendant(@RequestParam final int rendezvousId) {
		ModelAndView result;

		final Rendezvous rdv = this.rendezvousService.findOne(rendezvousId);
		if (rdv.getDeleted() == false) {
			Collection<User> user = new ArrayList<>();
			user = this.userService.findAttendants(rendezvousId);

			result = new ModelAndView("user/listAttendant");
			result.addObject("user", user);
			result.addObject("requestURI", "user/listAttendant.do");
		} else
			result = new ModelAndView("redirect:../");

		return result;
	}

}
