
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
	public void commentUser() {
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
			{
				//User creates a question for a rendezvous that he or she has created.
				"user1", "a question", "rendezvous1", null
			}, {
				//User creates a question for a rendezvous that he or she has not created.
				"user2", "another question", "rendezvous1", IllegalArgumentException.class
			}, {
				//Unauthenticated user creates a question.		
				null, "another more question", "rendezvous1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createCommentTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void createCommentTemplate(final String user, final String text, final String rendezvous, final Class<?> expected) {
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
