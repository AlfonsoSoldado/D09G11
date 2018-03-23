
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.FranchiseService;
import utilities.AbstractTest;
import domain.Franchise;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FranchiseTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private FranchiseService		franchiseService;

	@Test
	public void franchiseTest() {

		final Object testingData[][] = {
			{
				// Editing franchise
				"admin", "name 1", "https://foto.com/", "hello", "hola", null
			}, {
				// Leaving blank businessName
				"admin", "", "https://foto.com/", "hello", "hola", javax.validation.ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createFranchiseTemplate((String) testingData[i][0], (String) testingData[i][1], 
					(String) testingData[i][2], (String) testingData[i][3],(String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	public void createFranchiseTemplate(final String user, final String businessName, final String banner, final String welcomeEnglishMessage, final String welcomeSpanishMessage, final Class<?> expected) {
		Franchise franchise;
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(user);
			franchise = franchiseService.findAll().iterator().next();
			franchise.setBusinessName(businessName);
			franchise.setBanner(banner);
			franchise.setWelcomeEnglishMessage(welcomeEnglishMessage);
			franchise.setWelcomeSpanishMessage(welcomeSpanishMessage);
			this.unauthenticate();
			this.franchiseService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
