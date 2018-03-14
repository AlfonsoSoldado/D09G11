package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Announcement;
import domain.Category;
import domain.Rendezvous;
import domain.Services;
import domain.User;
import services.AdministratorService;
import services.CategoryService;
import services.ServicesService;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private ServicesService servicesService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categories = categoryService.findAll();
		result = new ModelAndView("category/administrator/list");
		result.addObject("category", categories);
		result.addObject("requestURI", "category/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Category category;

		category = this.categoryService.create();
		res = this.createEditModelAndView(category);

		ArrayList<Integer> levels = new ArrayList<>();
		levels.add(1);
		levels.add(2);
		levels.add(3);

		res.addObject("levels", levels);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Category category, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(category, "category.params.error");
		} else {
			try {
				if (category.getId() != 0) {

					Category old = this.categoryService.findOne(category.getId());
					if (old.getLevel() != category.getLevel()) {
						updateServices(old.getServices(), category);
					}

				}
				this.categoryService.save(category);

				result = new ModelAndView("redirect:list.do");
			} catch (Exception e) {
				result = createEditModelAndView(category, "category.commit.error");
			}
		}

		return result;
	}

	// TODO intentar cambiar los bucles
	private void updateServices(Collection<Services> servicesWithThisCategory, Category category) {
		for (Services services : servicesWithThisCategory) {
			if (services.getLevel() - category.getLevel() == 2 || services.getLevel() - category.getLevel() == -2) {
				Collection<Category> categories = services.getCategory();
				for (Category categoryremove : categories) {
					if (categoryremove.getLevel() - category.getLevel() == 2
							|| categoryremove.getLevel() - category.getLevel() == -2) {
						categories.remove(categoryremove);
					}
				}
				servicesService.save(services);

			}
		}
	}

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/administrator/edit");
		result.addObject("category", category);
		result.addObject("message", message);
		result.addObject("requestUri", "category/administrator/edit.do");

		return result;
	}

}
