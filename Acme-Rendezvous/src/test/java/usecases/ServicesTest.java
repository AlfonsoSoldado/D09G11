
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.RendezvousService;
import services.ServicesService;
import utilities.AbstractTest;
import domain.Rendezvous;
import domain.Services;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ServicesTest extends AbstractTest {

	@Autowired
	private ServicesService		servicesService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Test---------------------------------------------------------------
	@Test
	public void servicesTest() {
		final Object testingData[][] = {

			//An actor who is authenticated as a user must be able to:
			//1. List the services that are available in the system.
			//2. Request a service for one of the rendezvouses that he or she is created. He or she
			//must specify a valid credit card in every request for a service. 

			//An actor who is registered as a manager must be able to:
			//1. List the services that are available in the system.
			//2. Manage his or her services, which includes listing them, creating them, updating
			//them, and deleting them as long as they are not required by any rendezvouses.

			//An actor who is authenticated as an administrator must be able to:
			//Cancel a service that he or she finds inappropriate. Such services cannot be requested
			//for any rendezvous. They must be flagged appropriately when listed.

			{
				//User list services.
				"user1", null
			}, {
				//Manager list services.
				"manager1", null
			}, {
				//Unauthenticated actor list services.		
				null, IllegalArgumentException.class
			}, {
				//Manager creates a service.
				"manager1", "name1", "description1", false, 1, "rendezvous1", null
			}, {
				//User creates a service.
				"user1", "name2", "description2", false, 1, "rendezvous1", IllegalArgumentException.class
			}, {
				//Manager updates a service that she or he has created.
				"manager1", "nameEdit", "services1", null
			}, {
				//Manager updates a service that she or he has not created.
				"manager1", "nameEdit", "services2", IllegalArgumentException.class
			}, {
				//User updates a service.
				"user1", "nameEdit", "services1", IllegalArgumentException.class
			}, {
				//Manager deletes a service that he or she has created.
				"manager1", "services1", null
			}, {
				//Manager delete a service that he or she has not created.
				"manager1", "services2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < 3; i++)
			this.listTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

		for (int i = 3; i < 5; i++)
			this.createTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (boolean) testingData[i][3], (int) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);

		//		for (int i = 5; i < 8; i++)
		//			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

		for (int i = 8; i < testingData.length; i++)
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void deleteTemplate(final String user, final String services, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Delete Services-------------------
			this.authenticate(user);
			final int servicesId = this.getEntityId(services);
			final Services servicesFinded = this.servicesService.findOne(servicesId);
			this.servicesService.delete(servicesFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void editTemplate(final String user, final String name, final String services, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Edit Services-------------------
			this.authenticate(user);
			final int servicesId = this.getEntityId(services);
			final Services servicesFinded = this.servicesService.findOne(servicesId);
			servicesFinded.setName(name);
			this.servicesService.save(servicesFinded);
			this.unauthenticate();
			this.servicesService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void createTemplate(final String user, final String name, final String description, final boolean canceled, final int level, final String rendezvous, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Create Services-------------------
			this.authenticate(user);
			final Services services = this.servicesService.create();
			services.setName(name);
			services.setDescription(description);
			services.setCanceled(canceled);
			services.setLevel(level);
			final int rendezvousId = this.getEntityId(rendezvous);
			final Rendezvous rendezvousFinded = this.rendezvousService.findOne(rendezvousId);
			services.setRendezvous(rendezvousFinded);

			this.servicesService.save(services);
			this.unauthenticate();
			this.servicesService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void listTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------List Services-------------------
			this.authenticate(user);
			this.servicesService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
