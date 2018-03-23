package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.RSVPService;
import services.RendezvousService;
import utilities.AbstractTest;
import domain.RSVP;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class RSVPTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private RSVPService rsvpService;
	
	@Autowired
	private RendezvousService rendezvousService;
	
	// Creating and saving a question -----------------------------------------
	
	@Test
	public void driverRSVPCreateSave() {
		final Object testingData[][] = {
				{// User1 create a question for one of his rendezvous.
				"user1", true, "rendezvous1", null}, 
				{// User1 create a question for another rendezvous.
				"user2", false, "rendezvous2", IllegalArgumentException.class}, {
				// User cancel rsvp
				"user1", "RSVP2", IllegalArgumentException.class
				},
			};
			for (int i = 0; i < 2; i++)
				this.templateRSVPCreateSave((String) testingData[i][0], (boolean) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			
			for (int i = 2; i < testingData.length; i++)
				this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		}

	private void templateRSVPCreateSave(final String user, boolean confirmed, String rendezvous, Class<?> expected) {
		RSVP rsvp;
		Rendezvous rendezvousRSVP;
		Class<?> caught; 
		caught = null;
		try {
			super.authenticate(user);
			rendezvousRSVP = this.rendezvousService.findOne(this.getEntityId(rendezvous));
			rsvp = this.rsvpService.create();
			
			rsvp.setConfirmed(confirmed);
			rsvp.setRendezvous(rendezvousRSVP);
			
			rsvp = this.rsvpService.save(rsvp);
			this.unauthenticate();
			this.rsvpService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void editTemplate(final String user, final String rsvp, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------Edit Request-------------------
			this.authenticate(user);
			final int rsvpId = this.getEntityId(rsvp);
			final RSVP rsvpFinded = this.rsvpService.findOne(rsvpId);
			rsvpFinded.setConfirmed(false);
			this.rsvpService.save(rsvpFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
