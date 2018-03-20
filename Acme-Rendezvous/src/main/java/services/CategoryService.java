
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Services;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository	categoryRepository;
	@Autowired
	private ServicesService		servicesService;


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

	public Category findOne(final int category) {
		Assert.isTrue(category != 0);
		Category res;
		res = this.categoryRepository.findOne(category);
		return res;
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		this.checkAuthority();
		Category res = null;
		if (category.getId() != 0) {

			final Category old = this.findOne(category.getId());
			if ((old.getLevel() - category.getLevel() <= -1) && !old.getServices().isEmpty())
				this.updateServices(old.getServices(), category);
			else {
				category.setServices(new ArrayList<Services>());
				res = this.categoryRepository.save(category);
			}
		} else
			res = this.categoryRepository.save(category);

		return res;
	}
	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		Assert.isTrue(this.categoryRepository.exists(category.getId()));
		final Category old = this.findOne(category.getId());
		if (!old.getServices().isEmpty())
			this.updateServicesToRemove(old.getServices(), category);
		this.categoryRepository.delete(category);
	}

	// Other business method --------------------------------------------------

	public Collection<Category> findCategoryByServices(final int servicesId) {
		return this.categoryRepository.findCategoryByServices(servicesId);
	}

	public Collection<Category> findCategoryByLevel(final int level) {
		return this.categoryRepository.findCategoryByLevel(level);
	}

	private void updateServicesToRemove(final Collection<Services> services, final Category category) {
		for (final Services service : services) {
			final Collection<Category> categories = service.getCategory();
			categories.remove(category);
			service.setCategory(categories);
			this.servicesService.save(service);
		}

	}

	// TODO intentar cambiar los bucles
	private void updateServices(final Collection<Services> servicesWithThisCategory, final Category category) {
		final Collection<Services> categoryServices = category.getServices();

		for (final Services services : servicesWithThisCategory)
			if (services.getLevel() - category.getLevel() <= -1) {
				final Collection<Category> categories = services.getCategory();
				for (final Category categoryremove : categories)
					if (categoryremove.getLevel() - category.getLevel() <= -1)
						if (categories.size() == 1) {
							categories.clear();
							break;
						} else {
							categories.remove(categoryremove);
							categoryServices.remove(services);
						}
				category.setServices(categoryServices);
				this.categoryRepository.save(category);
				this.servicesService.save(services);

			}
	}

	public void flush() {
		this.categoryRepository.flush();
	}

	public void checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("ADMIN");
		Assert.isTrue(authority.contains(res));
	}
}
