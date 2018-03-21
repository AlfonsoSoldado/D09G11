
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AnswerService;
import services.QuestionService;
import utilities.AbstractTest;
import domain.Answer;
import domain.Question;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AnswerTest extends AbstractTest {

	@Autowired
	private AnswerService		answerService;

	@Autowired
	private QuestionService	questionService;


	// Test---------------------------------------------------------------
	@Test
	public void answerTest() {
		final Object testingData[][] = {

			//An actor who is authenticated as a user must be able to :
			//Manage the questions that are associated with a rendezvous that he or she is created previously.
			{
				//User creates a question for a rendezvous that he or she has created.
				"user1", "an answer", "question1", null
			}, {
				//User creates a question for a rendezvous that he or she has not created.
				"user2", "another answer", "question1", IllegalArgumentException.class
			}, {
				//Unauthenticated actor creates a question.		
				null, "another more answer", "question1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createAnswerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	protected void createAnswerTemplate(final String user, final String text, final String question, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Question-------------------
			this.authenticate(user);
			final Answer answer = this.answerService.create();
			answer.setText(text);
			final int questionId = this.getEntityId(question);
			final Question que = this.questionService.findOne(questionId);
			Assert.notNull(que);
			answer.setQuestion(que);

			this.answerService.save(answer);
			this.unauthenticate();
			this.answerService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
