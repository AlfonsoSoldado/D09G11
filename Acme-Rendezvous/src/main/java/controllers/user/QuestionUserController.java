
package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.QuestionService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Question;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/question/user")
public class QuestionUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private QuestionService		questionService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private UserService			userService;


	// Constructors ---------------------------------------------------------

	public QuestionUserController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int rendezvousId) {
		ModelAndView res;
		Rendezvous rendezvous;
		final User user = this.userService.findByPrincipal();
		rendezvous = this.rendezvousService.findOne(rendezvousId);
		if (rendezvous.getDeleted() == false && user.getRendezvous().contains(rendezvous)) {
			Question question;
			question = this.questionService.create();
			question.setRendezvous(rendezvous);
			res = this.createEditModelAndView(question);
		} else
			res = new ModelAndView("redirect:../../");

		return res;
	}
	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int questionId) {
		ModelAndView result;
		Question question;
		question = this.questionService.findOne(questionId);
		final Rendezvous rendezvous = this.rendezvousService.findRendezvousByQuestionId(questionId);
		final User user = this.userService.findByPrincipal();
		if (rendezvous.getDeleted() == false && user.getQuestion().contains(question))
			result = this.createEditModelAndView(question);
		else
			result = new ModelAndView("redirect:../../");

		return result;
	}
	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Question question, final BindingResult binding) {
		ModelAndView res;
		question = this.questionService.reconstruct(question, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(question, "question.params.error");
		else
			try {
				this.questionService.save(question);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(question, "question.commit.error");
			}
		return res;
	}

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Question question, final BindingResult binding) {
		ModelAndView res;
		try {
			this.questionService.delete(question);
			res = new ModelAndView("redirect:../../");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(question, "question.commit.error");
		}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Question question) {
		ModelAndView result;

		result = this.createEditModelAndView(question, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Question question, final String message) {
		ModelAndView result;

		result = new ModelAndView("question/edit");
		result.addObject("question", question);
		result.addObject("message", message);
		result.addObject("requestUri", "question/edit.do");

		return result;
	}
}
