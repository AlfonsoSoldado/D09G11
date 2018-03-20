
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CategoryService;
import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryTest extends AbstractTest {

	@Autowired
	private CategoryService	categoryService;


	/*
	 * An actor who is authenticated as an administrator must be able to:
	 * Create a new category
	 */

	@Test
	public void CategoryAdminCreate() {
		final Object testingData[][] = {

			{
				// An admin create a category
				"admin", "category078", "description1", 1, null
			}, {
				// An user create a category
				"user1", "category13", "description2", 1, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCategoryTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	private void templateCategoryTemplate(final String user, final String name, final String description, final int level, final Class<?> expected) {
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
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
