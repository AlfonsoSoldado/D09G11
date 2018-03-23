
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

			{
				// Answer the questions that are associated with a rendezvous that he or she RSVPing now.
				"user1", "an answer", "question1", null
			}, {
				"user2", "another answer", "question1", IllegalArgumentException.class
			}, {
				null, "another more answer", "question1", IllegalArgumentException.class
			}, {
				// Edit one of his answer
				"user1", "answer1", null
			}, {
				// Edit another answer
				"user1", "answer2", DataIntegrityViolationException.class
			}, {
				// Delete one of his answer
				"user1", "answer1", null
			}, {
				// Delete another answer
				"user1", "answer2", null
			}
		};

		for (int i = 0; i < 3; i++)
			this.createAnswerTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		
		for (int i = 3; i < 5; i++)
			this.editAnswerTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		
		for (int i = 5; i < testingData.length; i++)
			this.deleteAnswerTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	protected void createAnswerTemplate(final String user, final String text, final String question, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Answer-------------------
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
	
	private void editAnswerTemplate(final String user, final String answery, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user);
			int answerId = this.getEntityId(answery);
			Answer answer = this.answerService.findOne(answerId);
			answer.setText("something");
			this.answerService.save(answer);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
	
	private void deleteAnswerTemplate(final String user, final String answery, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			super.authenticate(user);
			int answerId = this.getEntityId(answery);
			Answer answer = this.answerService.findOne(answerId);
			this.answerService.delete(answer);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
