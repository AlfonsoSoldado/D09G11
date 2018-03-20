package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Category;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	private CategoryService categoryService;

	/*
	 * An actor who is authenticated as an administrator must be able to: create a
	 * new category
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void CategoryAdminCreate() {
		final Object testingData[][] = {

				// An admin create a new category
				{

						"category1", "description1", "1", null, null },
				{
						// An admin create other category
						"category1", "description1", "aaaa", null, IllegalArgumentException.class } };
		for (int i = 0; i < testingData.length; i++)
			this.templateCategoryCreateSave((String) testingData[i][0], (String) testingData[i][1],
					(int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	private void templateCategoryCreateSave(String name, String description, int level, Class<?> expected) {
		Category category;
		Class<?> caught;
		caught = null;
		try {
			super.authenticate("admin");
			category = this.categoryService.create();

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
