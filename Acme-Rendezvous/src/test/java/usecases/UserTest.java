
package usecases;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.UserAccount;
import services.UserService;
import utilities.AbstractTest;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class UserTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	@Autowired
	UserService	userService;


	//An actor who is not authenticated must be able to:
	//1. Register to the system as a user.
	//2. List the users of the system
	//. An actor who is authenticated as a user must be able to:
	//Do the same that unauthenticate actor but register

	@Test
	public void userTest() {

		final Object testingData[][] = {
			{
				//A existing user do login
				"user1", null
			}, {
				//A no existing user do login
				"user28", IllegalArgumentException.class
			}, {
				//A unauthenticated actor create a user
				null, null
			}, {
				//A user create a user
				"user1", RuntimeException.class
			}, {
				//A user list users
				"user1", null
			}, {
				//A unhautenticated actor list users
				null, null
			}
		};
		for (int i = 0; i < 2; i++)
			this.loginUserTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

		for (int i = 2; i < 4; i++)
			this.createUserTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

		for (int i = 4; i < testingData.length; i++)
			this.listUserTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void listUserTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			this.userService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	public void loginUserTemplate(final String user, final Class<?> expected) {
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

	public void createUserTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername("userTest");
			userAccount.setPassword("userTest");
			final List<Authority> authorities = new ArrayList<>();
			final Authority aut = new Authority();
			aut.setAuthority("USER");
			authorities.add(aut);
			userAccount.setAuthorities(authorities);

			final User userTest = this.userService.create();
			final String fecha = "1993/09/21";
			final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			userTest.setDateBorn(format.parse(fecha));
			userTest.setEmail("user@gmail.com");
			userTest.setName("user");
			userTest.setPhoneNumber("626253077");
			userTest.setPostalAddress(41000);
			userTest.setSurname("user");
			userTest.setUserAccount(userAccount);

			this.userService.save(userTest);
			this.unauthenticate();
			this.userService.flush();
			this.authenticate(userTest.getUserAccount().getUsername());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
