
package controllers.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RSVPService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/RSVP/user")
public class RSVPUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService			userService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private RSVPService			rsvpService;


	// Constructors ---------------------------------------------------------

	public RSVPUserController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		RSVP rsvp;

		rsvp = this.rsvpService.create();

		result = this.createModelAndView(rsvp);

		return result;
	}

	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RSVP r, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createModelAndView(r);
		else
			try {
				final RSVP saved = this.rsvpService.save(r);
				final User u = this.userService.findByPrincipal();

				if (!(this.rsvpService.questionsAnswered(saved.getRendezvous().getId(), u.getId())))
					res = this.createModelAndView(r, "commit.error.question");
				else {
					if (saved.getRendezvous().getAdultOnly() == true)
						Assert.isTrue(this.userService.is18(u));

					final Collection<RSVP> rsvps = u.getRsvp();
					rsvps.add(saved);
					u.setRsvp(rsvps);
					this.userService.save(u);
					final Rendezvous rendezvous = saved.getRendezvous();
					final Collection<User> att = rendezvous.getAttendant();
					att.add(u);
					rendezvous.setAttendant(att);

					this.rendezvousService.save(rendezvous);

					res = new ModelAndView("redirect:../../rendezvous/user/listAttendRendezvous.do");
				}
			} catch (final Throwable oops) {
				res = this.createModelAndView(r, "commit.error");
			}

		return res;
	}
	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int rendezvousId) {
		ModelAndView res;
		final User us = this.userService.findByPrincipal();
		final Rendezvous ren = this.rendezvousService.findOne(rendezvousId);
		final RSVP rsvp = this.rsvpService.findRSVPByUserAndRendezvous(us.getId(), rendezvousId);
		if (ren == null)
			res = new ModelAndView("redirect:../../rendezvous/user/listAttendRendezvous.do");
		else
			try {
				us.getRsvp().remove(rsvp);
				final User saved = this.userService.save(us);

				ren.getAttendant().remove(saved);
				this.rendezvousService.save(ren);

				this.rsvpService.delete(rsvp);

				res = new ModelAndView("redirect:../../rendezvous/user/listAttendRendezvous.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:../../rendezvous/user/listAttendRendezvous.do");
				res.addObject("message", "commit.error");
			}

		return res;
	}
	// Ancillary methods --------------------------------------------------

	protected ModelAndView createModelAndView(final RSVP rsvp) {
		ModelAndView result;

		result = this.createModelAndView(rsvp, null);

		return result;
	}

	protected ModelAndView createModelAndView(final RSVP rsvp, final String message) {
		ModelAndView result;

		final boolean f = Boolean.FALSE;
		final boolean t = Boolean.TRUE;

		final Collection<Boolean> confirmed = new ArrayList<>();
		confirmed.add(f);
		confirmed.add(t);

		final User user = this.userService.findByPrincipal();
		final Collection<Rendezvous> rendezvouses = this.rendezvousService.findNotYetAttendantByUserId(user.getId());

		result = new ModelAndView("RSVP/user/create");
		result.addObject("RSVP", rsvp);
		result.addObject("confirmed", confirmed);
		result.addObject("message", message);
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("requestUri", "RSVP/create.do");

		return result;
	}
}
