
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

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

			//An actor who is authenticated as a user must be able to:
			//Comment on the rendezvouses that he or she has RSVPd.
			//In addition to writing a comment from scratch, a user may reply to a comment
			{
				//User comment a rendezvous that he or she has RSVPd.
				"user1", "a comment", "http://www.foto.com", "rendezvous1", null, null
			}, {
				//User comment a rendezvous that he or she hasn't RSVPd.
				"user2", "another comment", "http://www.foto.com", "rendezvous1", null, null
			}, {
				//Unauthenticated user comment a rendezvous.		
				null, "another more comment", "http://www.foto.com", "rendezvous1", null, IllegalArgumentException.class
			}, {
				//User reply a comment
				"user2", "reply", "http://www.foto.com", "rendezvous1", "comment1", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createCommentTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);

	}
	protected void createCommentTemplate(final String user, final String text, final String picture, final String rendezvous, final String parent, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Comment-------------------
			this.authenticate(user);
			final Comment comment = this.commentService.create();
			comment.setText(text);
			comment.setPicture(picture);
			final int rendezvousId = this.getEntityId(rendezvous);
			final Rendezvous rdv = this.rendezvousService.findOne(rendezvousId);
			Assert.notNull(rdv);
			comment.setRendezvous(rdv);

			if (parent != null) {
				final int commentId = this.getEntityId(parent);
				final Comment parentComment = this.commentService.findOne(commentId);
				comment.setParent(parentComment);
			}

			this.commentService.save(comment);
			this.unauthenticate();
			this.commentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
