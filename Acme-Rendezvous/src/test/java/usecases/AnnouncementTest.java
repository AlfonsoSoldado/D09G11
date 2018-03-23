package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AnnouncementService;
import services.RendezvousService;
import utilities.AbstractTest;
import domain.Announcement;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class AnnouncementTest extends AbstractTest {

	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private RendezvousService rendezvousService;

	// Test---------------------------------------------------------------
	@Test
	public void announcementTest() {
		final Object testingData[][] = {

				// Create an announcement of his/her rendezvous
				{ "user1", "title 1", "description 1", "rendezvous1", null },{ 
				// Create an announcement of other rendezvous
				"user1", "title 2", "description 2", "rendezvous2", IllegalArgumentException.class }, { 
				//Listing announcement (without authentication)
				null, null },{ 
				//Listing announcement (with authentication)
				"user1", null },{ 
				//Deleting announcement (Admin)
				"admin1", "announcement1", null },{ 
				//Deleting announcement (User)
				"user1", "announcement1", IllegalArgumentException.class }};

		for (int i = 0; i < 2; i++)
			this.createAnnouncementTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (Class<?>) testingData[i][4]);

		for (int i = 2; i < 4; i++)
			this.listTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);

	}

	protected void createAnnouncementTemplate(final String user,
			final String title, final String description,
			final String rendezvous, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			// -----------------Announcement-------------------
			this.authenticate(user);
			final int rendezvousId = this.getEntityId(rendezvous);
			final Rendezvous rdv = this.rendezvousService.findOne(rendezvousId);
			final Announcement announcement = this.announcementService
					.create(rdv);
			announcement.setTitle(title);
			announcement.setDescription(description);

			this.announcementService.save(announcement);
			this.unauthenticate();
			this.announcementService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void listTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------List Announcement-------------------
			this.authenticate(user);
			this.announcementService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void deleteTemplate(final String user, final String announcement, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Delete Rendezvous-------------------
			this.authenticate(user);
			final int announcementId = this.getEntityId(announcement);
			final Announcement announcementFinded = this.announcementService.findOne(announcementId);
			this.announcementService.delete(announcementFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
