package controllers.administrator;

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

import controllers.AbstractController;
import domain.Category;
import domain.Services;
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

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		if (category != null) {

			result = this.createEditModelAndView(category);
			ArrayList<Integer> levels = new ArrayList<>();
			levels.add(1);
			levels.add(2);
			levels.add(3);

			result.addObject("levels", levels);

		} else
			result = new ModelAndView("redirect:list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Category category, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(category, "category.params.error");
		} else {
			try {
				if (category.getId() != 0) {

					Category old = this.categoryService.findOne(category.getId());
					if ((old.getLevel() - category.getLevel() <= -1) && !old.getServices().isEmpty()) {
						updateServices(old.getServices(), category);
					} else {
						category.setServices(new ArrayList<Services>());
						this.categoryService.save(category);

					}

				} else {

					this.categoryService.save(category);
				}
				result = new ModelAndView("redirect:list.do");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result = createEditModelAndView(category, "category.commit.error");
			}
		}

		return result;
	}

	// TODO intentar cambiar los bucles
	private void updateServices(Collection<Services> servicesWithThisCategory, Category category) {
		Collection<Services> categoryServices = category.getServices();

		for (Services services : servicesWithThisCategory) {
			if (services.getLevel() - category.getLevel() <= -1) {
				Collection<Category> categories = services.getCategory();
				for (Category categoryremove : categories) {
					if (categoryremove.getLevel() - category.getLevel() <= -1) {
						if (categories.size() == 1) {
							categories.clear();
							break;
						} else {
							categories.remove(categoryremove);
							categoryServices.remove(services);
						}
					}
				}
				category.setServices(categoryServices);
				categoryService.save(category);
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
