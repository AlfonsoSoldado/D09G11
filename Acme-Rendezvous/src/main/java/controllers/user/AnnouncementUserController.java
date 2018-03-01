
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Announcement;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/announcement/user")
public class AnnouncementUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;


	// Constructors ---------------------------------------------------------

	public AnnouncementUserController() {
		super();
	}

	// List --------------------------------------------------------------

	@RequestMapping(value = "/streamAnnouncement", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Announcement> announcements;
		final User u = this.userService.findByPrincipal();

		announcements = this.announcementService.findAnnouncementsByUser(u.getId());

		result = new ModelAndView("announcement/list");
		result.addObject("announcement", announcements);
		result.addObject("requestURI", "announcement/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int rendezvousId) {
		ModelAndView res;

		final User main = this.userService.findByPrincipal();
		Announcement announcement;
		Rendezvous rendezvous;

		rendezvous = this.rendezvousService.findOne(rendezvousId);
		if (main.getRendezvous().contains(rendezvous) && rendezvous.getDeleted() == false) {
			announcement = this.announcementService.create(rendezvous);
			res = this.createEditModelAndView(announcement);
		} else
			res = new ModelAndView("redirect:../../");

		return res;
	}
	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Announcement announcement, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(announcement, "announcement.params.error");
		else
			try {
				this.announcementService.save(announcement);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(announcement, "announcement.commit.error");
			}
		return res;
	}

	// Deleting --------------------------------------------------------------



	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Announcement announcement) {
		ModelAndView result;

		result = this.createEditModelAndView(announcement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Announcement announcement, final String message) {
		ModelAndView result;
		result = new ModelAndView("announcement/user/create");
		result.addObject("announcement", announcement);
		result.addObject("message", message);
		result.addObject("requestUri", "announcement/user/edit.do");
		return result;

	}
}
