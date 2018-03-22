
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AdministratorService;
import utilities.AbstractTest;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private AdministratorService	administratorService;

	@Test
	public void administratorTest() {

		final Object testingData[][] = {
			{
				"admin", null
			}, {
				"adminNotRegister", IllegalArgumentException.class
			}, {
//				"admin", "name", "surname", "mail@mail.com", 5258, null, null
//			}, {
//				"admin", "", "surname", "mail@mail.com", null, null, javax.validation.ConstraintViolationException.class
//			}, {
//				"admin", null, "surname", "mail@mail.com", null, null, javax.validation.ConstraintViolationException.class
//			}, {
//				"admin", "name", "", "mail@mail.com", null, null, javax.validation.ConstraintViolationException.class
//			}, {
//				"admin", "name", null, "mail@mail.com", null, null, javax.validation.ConstraintViolationException.class
//			}, {
//				"admin", "name", null, "mail@mail.com", null, null, javax.validation.ConstraintViolationException.class
			}
		};

		for (int i = 0; i < 2; i++)
			this.loginAdministratorTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		
//		for (int i = 2; i < testingData.length; i++)
//			this.editAdministratorTemplate((String) testingData[i][0], (String) testingData[i][1],  (String) testingData[i][2], 
//					(String) testingData[i][3], (int) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	public void loginAdministratorTemplate(final String user, final Class<?> expected) {
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
	
//	public void editAdministratorTemplate(final String user, final String name, final String surname, final String email,
//			final int postalAddress, final String phoneNumber, final Class<?> expected) {
//		Class<?> caught;
//		caught = null;
//		try {
//			this.authenticate(user);
//			final int administratorId = this.getEntityId(user);
//			Administrator administrator = this.administratorService.findOne(administratorId);
//			administrator.setName(name);
//			administrator.setSurname(surname);
//			administrator.setEmail(email);
//			administrator.setPostalAddress(postalAddress);
//			administrator.setPhoneNumber(phoneNumber);
//			this.administratorService.save(administrator);
//			this.unauthenticate();
//			this.administratorService.flush();
//		} catch (final Throwable oops) {
//			caught = oops.getClass();
//		}
//		this.checkExceptions(expected, caught);
//	}
}