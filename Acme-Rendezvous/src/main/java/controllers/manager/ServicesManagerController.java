package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CategoryService;
import services.ManagerService;
import services.RendezvousService;
import services.RequestService;
import services.ServicesService;
import controllers.AbstractController;
import domain.Category;
import domain.Manager;
import domain.Rendezvous;
import domain.Request;
import domain.Services;

@Controller
@RequestMapping("/services/manager")
public class ServicesManagerController extends AbstractController {

	private Request requestAttribute;

	@Autowired
	private ServicesService servicesService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private RendezvousService rendezvousService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private RequestService requestService;

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int requestId) {
		ModelAndView res;

		requestAttribute = requestService.findOne(requestId);

		Services services = this.servicesService.create();
		Rendezvous rendezvous = rendezvousService.findRendezvousByRequest(requestAttribute);

		services.setRendezvous(rendezvous);

		res = this.createEditModelAndView(services);

		return res;
	}

	@RequestMapping(value = "/listMyServices", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Manager manager = (Manager) this.actorService.findByPrincipal();
		Collection<Services> services = servicesService.servicesByManager(manager);

		result = new ModelAndView("services/listMyServices");
		result.addObject("services", services);
		result.addObject("requestURI", "services/manager/listMyServices.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int servicesId) {
		ModelAndView result;
		Services services;

		services = this.servicesService.findOne(servicesId);
		result = this.createEditModelAndView(services);
		Collection<Rendezvous> rendezvous = this.rendezvousService.findRendezvousNotCancelled();
		result.addObject("rendezvous", rendezvous);
		Collection<Category> categories = this.categoryService.findAll();
		result.addObject("categories", categories);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Services services, final BindingResult binding) {
		ModelAndView res = null;
		this.managerService.checkAuthority();
		services.getCategory().remove(null);
		services = this.servicesService.reconstruct(services, binding);
		
		Manager manager = managerService.findByPrincipal();
		Rendezvous rendezvous = services.getRendezvous();

		services.setManager(manager);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(services, "services.params.error");
		} else if (services.getCategory().size() <= 3 && services.getCategory().size() > 1) {
			ArrayList<Category> categories = new ArrayList<>();
			categories.addAll(services.getCategory());
			if (categories.get(0).getLevel() == categories.get(1).getLevel()
					|| categories.get(0).getLevel() == categories.get(2).getLevel()
					|| categories.get(1).getLevel() == categories.get(2).getLevel()) {
				res = this.createEditModelAndView(services, "services.params.error");

			}
		} else
			try {
				Services saved = this.servicesService.save(services);
				rendezvous.setServices(saved);
				requestAttribute.setServices(saved);
				requestService.save(requestAttribute);

				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				System.out.println(oops.getCause());
				System.out.println(oops.getLocalizedMessage());
				System.out.println(oops.getStackTrace());
				System.out.println(oops.getSuppressed());
				res = this.createEditModelAndView(services, "services.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Services services, final BindingResult binding) {
		ModelAndView res;
		try {
			this.servicesService.delete(services);
			res = new ModelAndView("redirect:../../");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getCause());
			System.out.println(oops.getLocalizedMessage());
			System.out.println(oops.getStackTrace());
			System.out.println(oops.getSuppressed());
			res = this.createEditModelAndView(services, "services.commit.error");
		}
		return res;
	}

	protected ModelAndView createEditModelAndView(final Services services) {
		ModelAndView result;

		result = this.createEditModelAndView(services, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Services services, final String message) {
		ModelAndView result;
		Collection<Category> category1 = new ArrayList<Category>();
		Collection<Category> category2 = new ArrayList<Category>();
		Collection<Category> category3 = new ArrayList<Category>();

		category1 = categoryService.findCategoryByLevel(1);
		category2 = categoryService.findCategoryByLevel(2);
		category3 = categoryService.findCategoryByLevel(3);

		result = new ModelAndView("services/manager/edit");
		result.addObject("services", services);
		result.addObject("categories1", category1);
		result.addObject("categories2", category2);
		result.addObject("categories3", category3);
		result.addObject("message", message);
		result.addObject("requestUri", "services/manager/edit.do");

		return result;
	}

}
