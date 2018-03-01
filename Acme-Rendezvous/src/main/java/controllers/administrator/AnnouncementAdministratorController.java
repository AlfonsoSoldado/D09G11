package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import controllers.AbstractController;
import domain.Announcement;

@Controller
@RequestMapping("/announcement/administrator")
public class AnnouncementAdministratorController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private AnnouncementService announcementService;

	// Constructors ---------------------------------------------------------

	public AnnouncementAdministratorController() {
		super();
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int announcementId) {
		ModelAndView result;
		Announcement announcement;

		announcement = this.announcementService.findOne(announcementId);
		result = this.createEditModelAndView(announcement);

		return result;
	}
	
	// Deleting --------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(@Valid final Announcement announcement,
				final BindingResult binding) {
			ModelAndView res;
			try {
				this.announcementService.delete(announcement);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(announcement, "announcement.commit.error");
			}
			return res;
		}
	

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Announcement announcement) {
		ModelAndView result;

		result = this.createEditModelAndView(announcement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Announcement announcement,
			final String message) {
		ModelAndView result;
		
		result = new ModelAndView("announcement/edit");
		result.addObject("announcement", announcement);
		result.addObject("message", message);
		result.addObject("requestUri", "announcement/administrator/edit.do");
		
		return result;
	}
}
