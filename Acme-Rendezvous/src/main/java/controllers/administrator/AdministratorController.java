/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Rendezvous;
import services.AdministratorService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService administratorService;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result = new ModelAndView("administrator/display");
		result.addObject("averageRedezvousUser", administratorService.averageRedezvousUser());
		result.addObject("estandardDesviationRedezvousUser", administratorService.estandardDesviationRedezvousUser());
		result.addObject("ratioUserConRendezvous", administratorService.ratioUserConRendezvous());
		result.addObject("ratioUserSinRendezvous", administratorService.ratioUserSinRendezvous());
		result.addObject("averageUsersRendezvous", administratorService.averageUsersRendezvous());
		result.addObject("estandardDesviationUsersRendezvous",
				administratorService.estandardDesviationUsersRendezvous());
		result.addObject("averageRendezvousRSVPTruePerUser", administratorService.averageRendezvousRSVPTruePerUser());
		result.addObject("estandardDesviationRendezvousRSVPTruePerUser",
				administratorService.estandardDesviationRendezvousRSVPTruePerUser());
		result.addObject("averageannouncementsRendezvous", administratorService.averageannouncementsRendezvous());
		result.addObject("estandardDesviationAnnouncementsUser",
				administratorService.estandardDesviationAnnouncementsUser());
		List<Rendezvous> rendezvous = new ArrayList<>();
		rendezvous.addAll(administratorService.redezvousSimiliars10());
		if (rendezvous.isEmpty()) {
			rendezvous = null;
		}

		result.addObject("redezvousSimiliars10", rendezvous);
		result.addObject("topRedezvous", this.administratorService.topRendezvous());

		result.addObject("averageNumberOfQuestionsPerRendezvous",
				administratorService.averageNumberOfQuestionsPerRendezvous());
		result.addObject("estandardDesviationOfQuestionsPerRendezvous",
				administratorService.estandardDesviationOfQuestionsPerRendezvous());
		result.addObject("averageOfAnswerPerQuestionsPerRendezvous",
				administratorService.averageOfAnswerPerQuestionsPerRendezvous());
		result.addObject("estandardDesviationOfAnswerPerQuestionsPerRendezvous",
				administratorService.estandardDesviationOfAnswerPerQuestionsPerRendezvous());
		result.addObject("RendezvousConMas075Announcement", administratorService.RendezvousConMas075Announcement());
		result.addObject("averageRepliesComment", administratorService.averageRepliesComment());
		result.addObject("averageRepliesComment", administratorService.averageRepliesComment());
		result.addObject("estandardDesviationRepliesComment", administratorService.estandardDesviationRepliesComment());

		// *********
		return result;
	}

}
