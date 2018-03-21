
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorTest extends AbstractTest {

	// Supporting services ----------------------------------------------------

	@Test
	public void administratorTest() {

		final Object testingData[][] = {
			{
				"admin", null
			}, {
				"adminNotRegister", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createAdministratorTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void createAdministratorTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
