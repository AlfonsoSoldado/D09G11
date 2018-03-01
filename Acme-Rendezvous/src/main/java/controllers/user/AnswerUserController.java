package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import services.QuestionService;
import controllers.AbstractController;
import domain.Answer;
import domain.Question;

@Controller
@RequestMapping("/answer/user")
public class AnswerUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private QuestionService questionService;
	
	// Constructors ---------------------------------------------------------

	public AnswerUserController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int questionId) {
		ModelAndView res;
		Answer answer;
		Question question;

		answer = this.answerService.create();
		question = questionService.findOne(questionId);
		answer.setQuestion(question);
		
		res = this.createEditModelAndView(answer);

		return res;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int answerId) {
		ModelAndView result;
		Answer answer;

		answer = this.answerService.findOne(answerId);
		result = this.createEditModelAndView(answer);

		return result;
	}
	
	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Answer answer,
			final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(answer, "rendezvous.params.error");
		else
			try {
				this.answerService.save(answer);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(answer, "rendezvous.commit.error");
			}
		return res;
	}

	// Deleting --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Answer answer,
			final BindingResult binding) {
		ModelAndView res;
		try {
			this.answerService.delete(answer);
			res = new ModelAndView("redirect:../../");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(answer, "rendezvous.commit.error");
		}
		return res;
	}

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Answer answer) {
		ModelAndView result;

		result = this.createEditModelAndView(answer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Answer answer,
			final String message) {
		ModelAndView result;
		
		result = new ModelAndView("answer/edit");
		result.addObject("answer", answer);
		result.addObject("message", message);
		result.addObject("requestUri", "answer/edit.do");
		
		return result;
	}
}
