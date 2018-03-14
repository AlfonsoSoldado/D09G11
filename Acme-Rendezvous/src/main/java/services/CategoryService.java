package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.Services;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository categoryRepository;

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
		Category res;
		res = this.categoryRepository.save(category);
		return res;
	}

	public void delete(Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		Assert.isTrue(this.categoryRepository.exists(category.getId()));
		this.categoryRepository.delete(category);
	}

	// Other business method --------------------------------------------------

	public Collection<Category> findCategoryByServices(int servicesId) {
		return this.categoryRepository.findCategoryByServices(servicesId);
	}

	

	
}
