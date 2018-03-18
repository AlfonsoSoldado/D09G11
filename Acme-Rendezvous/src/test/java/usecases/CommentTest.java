
package usecases;

import java.util.Date;
import java.util.List;

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

	@SuppressWarnings("unchecked")
	@Test
	public void commentUser() {
		final Object testingData[][] = {

			//Create a comment
			{
				"a comment", null, "http://www.foto.com", null, null, "rendezvous1", null
			}, {
				//Create a comment with an invalid picture (url)
				"another comment", null, "foto", null, null, "rendezvous1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createCommentTemplate((String) testingData[i][0], (Date) testingData[i][1], (String) testingData[i][2],
					(List<Comment>) testingData[i][3], (Comment) testingData[i][4], 
					super.getEntityId((String) testingData[i][5]), (Class<?>) testingData[i][6]);

	}
	protected void createCommentTemplate(final String text, final Date momentMade, final String picture, 
			final List<Comment> replies, final Comment parent, final int rendezvouseId, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Comment-------------------
			this.authenticate("user2");
			final Comment comment = this.commentService.create();
			comment.setText(text);
			comment.setMomentMade(momentMade);
			comment.setPicture(picture);
			final Rendezvous rdv = this.rendezvousService.findOne(rendezvouseId);
			comment.setRendezvous(rdv);

			this.commentService.save(comment);
			this.unauthenticate();
			this.commentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
