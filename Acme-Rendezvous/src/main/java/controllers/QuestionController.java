package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.QuestionService;
import services.UserService;
import domain.Question;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/question")
public class QuestionController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;

	// Constructors ---------------------------------------------------------

	public QuestionController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/listByUser", method = RequestMethod.GET)
	public ModelAndView listByUser(@RequestParam int rendezvousId) {
		ModelAndView result;
		Collection<Question> question;
		
		Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
		Collection<Integer> rendezvousesId = new ArrayList<Integer>();
		User user;

		if(userService.checkUserLogged()){
			user = userService.findByPrincipal();
			rendezvouses.addAll(user.getRendezvous());
			for(Rendezvous r: rendezvouses){
				rendezvousesId.add(r.getId());
			}
		}

		question = questionService.findQuestionByRendezvous(rendezvousId);

		result = new ModelAndView("question/listByUser");
		result.addObject("question", question);
		result.addObject("rendezvousesId", rendezvousesId);
		result.addObject("requestURI", "question/listByUser.do");

		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int rendezvousId) {
		ModelAndView result;
		Collection<Question> question;

		question = questionService.findQuestionByRendezvous(rendezvousId);

		result = new ModelAndView("question/list");
		result.addObject("question", question);
		result.addObject("requestURI", "question/list.do");

		return result;
	}

}
