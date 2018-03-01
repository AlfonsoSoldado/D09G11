
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import services.RendezvousService;
import domain.Announcement;
import domain.Rendezvous;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController extends AbstractController {

	@Autowired
	private AnnouncementService	announcementService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Constructors ---------------------------------------------------------

	public AnnouncementController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvousId) {
		ModelAndView result;
		final Collection<Announcement> announcement;
		final Rendezvous rdv = this.rendezvousService.findOne(rendezvousId);
		if (rdv.getDeleted() == false) {

			announcement = this.announcementService.findAnnouncementsByRendezvous(rendezvousId);

			result = new ModelAndView("announcement/list");
			result.addObject("announcement", announcement);
			result.addObject("requestURI", "announcement/list.do");
		} else
			result = new ModelAndView("redirect:../");
		return result;
	}
}
