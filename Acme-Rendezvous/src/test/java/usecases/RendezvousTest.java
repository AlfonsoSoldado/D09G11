
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.RendezvousService;
import utilities.AbstractTest;
import domain.Question;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RendezvousTest extends AbstractTest {

	@Autowired
	private RendezvousService	rendezvousService;


	// Test---------------------------------------------------------------
	@Test
	public void rendezvousTest() {
		final Object testingData[][] = {

			//An actor who is not authenticated must be able to:
			//List the rendezvouses in the system 

			//An actor who is authenticated as a user must be able to:
			//1.List the rendezvouses in the system 
			//2.Create a rendezvous, which he is implicitly assumed to attend. Note that a user may
			//edit his or her rendezvouses as long as they are not saved them in final mode. Once a
			//rendezvous is saved in final mode, it cannot be edited or deleted by the creator.
			//3.Update or delete the rendezvouses that he or she is created. Deletion is virtual, that
			//is: the information is not removed from the database, but the rendezvous cannot be
			//updated. Deleted rendezvouses are flagged as such when they are displayed.
			//4. List the rendezvouses that he or she is RSVPd.
			//5. Link one of the rendezvouses that he or she is created to other similar rendezvouses. 

			//An actor who is authenticated as an administrator must be able to:
			//Remove a rendezvous that he or she thinks is inappropriate. 

			{
				//Not authenticated actor list rendezvous.
				null, null
			}, {
				//User list rendezvous.
				"user1", null
			}, {
				//User creates rendezvous.
				//user, name, description, fecha, finalMode, adultOnly, exception
				"user2", "name", "description", "2021/05/04", false, false, null
			}, {
				//Not authenticated actor creates rendezvous.
				null, "name", "description", "2021/05/04", false, false, IllegalArgumentException.class
			}, {
				//User edit a rendezvous in no finalMode that he or she has created.
				"user2", "edited name", "rendezvous2", null
			}, {
				//User edit a rendezvous in finalMode that he or she has created.
				"user1", "edited name", "rendezvous1", IllegalArgumentException.class
			}, {
				//User edit a rendezvous that he or she has not created.
				"user1", "edited name", "rendezvous2", IllegalArgumentException.class
			}, {
				//Not authenticated actor edit a rendezvous.
				null, "edited name", "rendezvous2", IllegalArgumentException.class
			}, {
				//User delete a rendezvous that he or she has created.
				"user2", "rendezvous2", null
			}, {
				//User delete a rendezvous that he or she has not created.
				"user1", "rendezvous2", IllegalArgumentException.class
			}, {
				//User list rendezvous that he or she attend.
				"user1", null
			}, {
				//User link rendezvous that he or she has created.
				"user2", "rendezvous2", "rendezvous1", null
			}, {
				//User link rendezvous that he or she has not created.
				"user2", "rendezvous1", "rendezvous2", IllegalArgumentException.class
			}, {
				//Admin remove a rendezvous 
				"admin", "rendezvous2", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listCommentTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	protected void listCommentTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Question-------------------
			this.authenticate(user);
			final Question question = this.questionService.create();
			question.setText(text);
			final int rendezvousId = this.getEntityId(rendezvous);
			final Rendezvous rdv = this.rendezvousService.findOne(rendezvousId);
			Assert.notNull(rdv);
			question.setRendezvous(rdv);

			this.questionService.save(question);
			this.unauthenticate();
			this.questionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
