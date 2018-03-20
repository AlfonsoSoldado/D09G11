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
import services.CategoryService;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService categoryService;

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

				this.categoryService.save(category);

				result = new ModelAndView("redirect:list.do");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result = createEditModelAndView(category, "category.commit.error");
			}
		}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Category category, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(category, "category.params.error");
		} else {
			try {

				
				categoryService.delete(category);
				result = new ModelAndView("redirect:list.do");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				result = createEditModelAndView(category, "category.commit.error");
			}
		}

		return result;
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
