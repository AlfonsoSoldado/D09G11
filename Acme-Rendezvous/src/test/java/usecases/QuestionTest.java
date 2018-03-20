
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.QuestionService;
import services.RendezvousService;
import utilities.AbstractTest;
import domain.Question;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class QuestionTest extends AbstractTest {

	@Autowired
	private QuestionService		questionService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Test---------------------------------------------------------------
	@Test
	public void commentUser() {
		final Object testingData[][] = {

			//An actor who is authenticated as a user must be able to :
			//Manage the questions that are associated with a rendezvous that he or she is created previously.
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
