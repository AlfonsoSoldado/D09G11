package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Category;
import domain.Services;
import repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ServicesService servicesService;
	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Category create() {
		Category category;
		category = new Category();
		return category;
	}

	public Collection<Category> findAll() {
		Collection<Category> res;
		res = this.categoryRepository.findAll();
		return res;
	}

	public Category findOne(int category) {
		Assert.isTrue(category != 0);
		Category res;
		res = this.categoryRepository.findOne(category);
		return res;
	}

	public Category save(Category category) {
		Assert.notNull(category);
		Category res = null;
		if (category.getId() != 0) {

			Category old = this.findOne(category.getId());
			if ((old.getLevel() - category.getLevel() <= -1) && !old.getServices().isEmpty()) {
				this.updateServices(old.getServices(), category);
			} else {
				category.setServices(new ArrayList<Services>());
				res = this.categoryRepository.save(category);
			}
		} else {

			res = this.categoryRepository.save(category);
		}

		return res;
	}

	public void delete(Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		Assert.isTrue(this.categoryRepository.exists(category.getId()));
		Category old = this.findOne(category.getId());
		if (!old.getServices().isEmpty()) {
			this.updateServicesToRemove(old.getServices(), category);
		}
		this.categoryRepository.delete(category);
	}

	// Other business method --------------------------------------------------

	public Collection<Category> findCategoryByServices(int servicesId) {
		return this.categoryRepository.findCategoryByServices(servicesId);
	}

	public Collection<Category> findCategoryByLevel(int level) {
		return this.categoryRepository.findCategoryByLevel(level);
	}

	private void updateServicesToRemove(Collection<Services> services, Category category) {
		for (Services service : services) {
			Collection<Category> categories = service.getCategory();
			categories.remove(category);
			service.setCategory(categories);
			servicesService.save(service);
		}

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
				categoryRepository.save(category);
				servicesService.save(services);

			}
		}

	}

	public void flush() {
		categoryRepository.flush();
	}
}
