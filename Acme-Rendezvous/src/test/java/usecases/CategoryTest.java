
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
	 * An actor who is authenticated as an administrator must be able to: Create a
	 * new category
	 */

	@Test
	public void CategoryAdminTest() {
		final Object testingData[][] = {

				{
						// An admin create a category
						"admin", "category078", "description1", 1, null },
				{
						// An user create a category
						"user1", "category13", "description2", 1, IllegalArgumentException.class },
				{
						// An admin update a category
						"admin", "category1", "description2", null },
				{
						// An user update a category
						"user1", "category1", "description2", IllegalArgumentException.class },
				{
						// An admin delete a category
						"admin", "category1", null },
				{
						// An user delete a category
						"user1", "category2", IllegalArgumentException.class } };
		for (int i = 0; i < 2; i++)
			this.createTemplateCategoryTemplate((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);

		for (int i = 2; i < 4; i++) {
			this.editTemplateCategoryTemplate((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (Class<?>) testingData[i][3]);
		}
		
		for (int i = 4; i < 6; i++) {
			this.deleteTemplateCategoryTemplate((String) testingData[i][0], (String) testingData[i][1],
					(Class<?>) testingData[i][2]);
		}
	}

	private void deleteTemplateCategoryTemplate(String user, String category, Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------Delete Rendezvous-------------------
			this.authenticate(user);
			final int categoryId = this.getEntityId(category);
			final Category categoryFinded = this.categoryService.findOne(categoryId);
			this.categoryService.delete(categoryFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	private void editTemplateCategoryTemplate(final String user, final String name, final String description,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------Edit Rendezvous-------------------
			this.authenticate(user);
			final int categoryId = this.getEntityId(name);
			final Category categoryFinded = this.categoryService.findOne(categoryId);
			categoryFinded.setDescription(description);
			this.categoryService.save(categoryFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	private void createTemplateCategoryTemplate(final String user, final String name, final String description,
			final int level, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			Category category = this.categoryService.create();
			category.setName(name);
			category.setDescription(description);
			category.setLevel(level);
			category = this.categoryService.save(category);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
