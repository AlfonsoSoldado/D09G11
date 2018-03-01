package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnswerService;
import domain.Answer;

@Controller
@RequestMapping("/answer")
public class AnswerController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private AnswerService answerService;

	// Constructors ---------------------------------------------------------

	public AnswerController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int questionId) {
		ModelAndView result;
		Collection<Answer> answer;

		answer = answerService.findAnswersByQuestion(questionId);

		result = new ModelAndView("answer/list");
		result.addObject("answer", answer);
		result.addObject("requestURI", "answer/list.do");

		return result;
	}

}
