package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Answer;
import domain.Question;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class QuestionServiceTest extends AbstractTest {

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private RendezvousService rendezvousService;
	
	@Autowired
	private AnswerService answerService;
	
	// Creating and saving a question -----------------------------------------
	
	@Test
	public void driverQuestionCreateSave() {
		final Object testingData[][] = {
				{// User1 create a question for one of his rendezvous.
					"question1", "answer1", "rendezvous1", null}, 
//					{// User1 create a question for another rendezvous.
//					"question2", "answer2", "rendezvous2", IllegalArgumentException.class}
			};
			for (int i = 0; i < testingData.length; i++)
				this.templateQuestionCreateSave((String) testingData[i][0], 
						(String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
		}

	private void templateQuestionCreateSave(String text, String answer, String rendezvous, Class<?> expected) {
		Question question;
		Rendezvous questionRendezvous;
		Answer questionAnswer;
		Collection<Answer> answers;
		Class<?> caught; 
		caught = null;
		try {
			authenticate("user1");;
			question = this.questionService.create();
			questionRendezvous = this.rendezvousService.findOne(this.getEntityId(rendezvous));
			questionAnswer = this.answerService.findOne(this.getEntityId(answer));
			answers = new ArrayList<Answer>();
			
			question.setRendezvous(questionRendezvous);
			question.setText(text);
			answers.add(questionAnswer);
			question.setAnswer(answers);
			
			question = this.questionService.save(question);
			this.unauthenticate();
			this.questionService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
