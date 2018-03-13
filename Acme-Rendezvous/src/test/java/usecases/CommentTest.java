
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CommentService;
import services.RendezvousService;
import utilities.AbstractTest;
import domain.Comment;
import domain.Rendezvous;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentTest extends AbstractTest {

	@Autowired
	private CommentService		commentService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Test---------------------------------------------------------------

	/*
	 * An actor who is authenticated as a user must be able to comment on the rendezvouses that he or she has RSVPd.
	 */

	@Test
	public void commentUser() {
		final Object testingData[][] = {

			//Create a comment
			{
				"comment1", "http://www.foto.com", null, "user1", "trip1"
			}, {
				//Create a comment with an invalid picture (url)
				"comment2", "foto", IllegalArgumentException.class, "user1", "trip1"
			}, {
				//Create a comment with an non register actor
				"comment2", "foto", IllegalArgumentException.class, null, "trip1"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createCommentTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4]);

	}
	protected void createCommentTemplate(final String text, final String picture, final Class<?> expected, final String user, final String rendezvous) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Comment-------------------
			this.authenticate(user);
			final Comment comment = this.commentService.create();
			comment.setText(text);
			comment.setPicture(picture);
			final int id = this.getEntityId(rendezvous);
			final Rendezvous rdv = this.rendezvousService.findOne(id);
			comment.setRendezvous(rdv);

			this.commentService.save(comment);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
