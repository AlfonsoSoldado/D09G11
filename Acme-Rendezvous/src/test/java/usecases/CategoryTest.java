package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Category;
import services.CategoryService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class CategoryTest extends AbstractTest {

	@Autowired
	private CategoryService categoryService;

	/*
	 * An actor who is authenticated as an administrator must be able to: create a
	 * new category
	 */

	@Test
	public void CategoryAdminCreate() {
		final Object testingData[][] = {

				// An admin create a new category
				{

						"admin", "category078", "description1", "1", null },
				{
						// An admin create other category
						"user1", "category13", "description1", "1", IllegalArgumentException.class } };
		for (int i = 0; i < testingData.length; i++)
			this.templateCategoryTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],  (Class<?>) testingData[i][4]);
	}

	private void templateCategoryTemplate(String user, String name, String description, String level,
			Class<?> expected) {
		Category category;
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user);
			category = this.categoryService.create();
			category.setName(name);
			category.setDescription(description);
			category.setLevel(Integer.valueOf(level));

			category = this.categoryService.save(category);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
