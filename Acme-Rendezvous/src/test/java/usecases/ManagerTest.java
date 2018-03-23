
package usecases;

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
import services.ManagerService;
import utilities.AbstractTest;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ManagerTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ManagerService	managerService;

	@Test
	public void managerTest() {

		final Object testingData[][] = {
			{
				"manager1", null
			}, {
				"manager28", IllegalArgumentException.class
			}, {
				//A unauthenticated actor create a user
				null, null
			}, {
				//A user create a user
				"user1", RuntimeException.class
			}
		};
		for (int i = 0; i < 2; i++)
			this.loginManagerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		
		for (int i = 2; i < testingData.length; i++)
			this.createManagerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void loginManagerTemplate(final String user, final Class<?> expected) {
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
	
	public void createManagerTemplate(final String manager, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(manager);
			final UserAccount userAccount = new UserAccount();
			userAccount.setUsername("managerTest");
			userAccount.setPassword("managerTest");
			final List<Authority> authorities = new ArrayList<>();
			final Authority aut = new Authority();
			aut.setAuthority("MANAGER");
			authorities.add(aut);
			userAccount.setAuthorities(authorities);

			final Manager managerTest = this.managerService.create();
			managerTest.setEmail("user@gmail.com");
			managerTest.setName("manager");
			managerTest.setPhoneNumber("626253077");
			managerTest.setPostalAddress(41000);
			managerTest.setSurname("manager");
			managerTest.setUserAccount(userAccount);

			this.managerService.save(managerTest);
			this.unauthenticate();
			this.managerService.flush();
			this.authenticate(managerTest.getUserAccount().getUsername());
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
