package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import domain.Manager;
import forms.ManagerForm;

@Controller
@RequestMapping("/manager")
public class RegisterManagerController extends AbstractController {

	// Services -------------------------------------------------------------
	
	@Autowired
	private ManagerService managerService;

	// Constructors ---------------------------------------------------------

	public RegisterManagerController() {
		super();
	}
	
	// Registering ----------------------------------------------------------
	
	@RequestMapping(value = "/register_Manager", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;
		Manager manager;
		manager = this.managerService.create();
		
		ManagerForm managerForm;
		managerForm = new ManagerForm(manager);
		
		res = new ModelAndView("manager/register_Manager");
		res.addObject("managerForm", managerForm);
		
		return res;
	}
	
	@RequestMapping(value = "/register_Manager", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("managerForm") ManagerForm managerForm,
			final BindingResult binding) {
		ModelAndView res;
		managerForm = this.managerService.reconstruct(managerForm, binding);
		if (binding.hasErrors()) {
			res = this.createEditModelAndView(managerForm, "actor.params.error");
		} else {
			try {
				if ((managerForm.getManager().getId() == 0)) {
					Assert.isTrue(managerForm.getManager().getUserAccount().getPassword().equals(managerForm.getConfirmPassword()), "password does not match");
//					Assert.isTrue(managerForm.getTerms(), "the conditions must be accepted");
				}
				this.managerService.save(managerForm.getManager());
				res = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("password does not match"))
					res = this.createEditModelAndView(managerForm, "actor.password.check");
//				else if (oops.getMessage().equals("the conditions must be accepted"))
//					res = this.createEditModelAndView(managerForm, "actor.terms.conditions");
				else if (oops.getMessage().equals("could not execute statement; SQL [n/a]; constraint [null]" + "; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"))
					res = this.createEditModelAndView(managerForm, "actor.commit.duplicate");
				else
					res = this.createEditModelAndView(managerForm, "actor.commit.error");
			}
		}
		return res;
	}
	
	// Ancillary methods --------------------------------------------------
	
		protected ModelAndView createEditModelAndView(final ManagerForm managerForm) {
			ModelAndView result;

			result = this.createEditModelAndView(managerForm, null);

			return result;
		}
		
		protected ModelAndView createEditModelAndView(final ManagerForm managerForm,
				final String message) {
			ModelAndView result;

			result = new ModelAndView("manager/register_Manager");
			result.addObject("manager", managerForm);
			result.addObject("message", message);
			
			return result;
		}
}
