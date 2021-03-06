
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentRepository		commentRepository;

	// Services ---------------------------------------------------------------

	@Autowired
	private RendezvousService		rendezvousService;

	@Autowired
	private UserService				userService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructor ------------------------------------------------------------

	public CommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Comment create() {
		this.userService.checkAuthority();
		Comment result;
		result = new Comment();
		result.setReplies(new ArrayList<Comment>());
		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;
		result = this.commentRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Comment findOne(final int commentId) {
		Comment result;
		result = this.commentRepository.findOne(commentId);
		return result;
	}

	public Comment save(final Comment comment) {
		this.userService.checkAuthority();
		Assert.isTrue(comment.getRendezvous().getAttendant().contains(this.userService.findByPrincipal()));
		Comment result;
		Assert.notNull(comment);
		if (comment.getId() == 0) {
			Date momentMade;
			momentMade = new Date(System.currentTimeMillis() - 1000);
			comment.setMomentMade(momentMade);
		}
		result = this.commentRepository.saveAndFlush(comment);
		if (comment.getParent() != null)
			this.updatePadre(comment.getParent(), result);
		this.updateRendezvous(comment.getRendezvous(), result);
		this.updateUser(result);

		return result;
	}

	public void delete(final Comment comment) {
		Assert.notNull(comment);
		Assert.isTrue(comment.getId() != 0);
		this.commentRepository.delete(comment);
	}

	public Comment deleteAdmin(final Comment comment) {
		this.administratorService.checkAuthority();
		Comment res;
		Comment result;
		Assert.notNull(comment);
		result = this.commentRepository.findOne(comment.getId());
		result.setText("this comment has been deleted, este comentario ha sido borrado");
		result.setPicture(null);
		res = this.commentRepository.saveAndFlush(result);

		return res;
	}
	// Other business method --------------------------------------------------

	private void updatePadre(final Comment padre, final Comment hijo) {
		final ArrayList<Comment> replies = new ArrayList<Comment>();
		if (padre.getReplies() != null)
			replies.addAll(padre.getReplies());
		replies.add(hijo);
		padre.setReplies(replies);
		this.commentRepository.saveAndFlush(padre);
	}

	private void updateUser(final Comment comment) {
		User user;
		user = this.userService.findByPrincipal();

		Collection<Comment> comments = new ArrayList<Comment>();
		comments = user.getComment();

		comments.add(comment);
		user.setComment(comments);

		this.userService.save(user);
	}

	public Collection<Comment> findCommentsByRendezvous(final int id) {
		final Collection<Comment> res = new ArrayList<Comment>();
		res.addAll(this.commentRepository.findCommentsByRendezvous(id));
		Assert.notNull(res);
		return res;

	}

	public void updateRendezvous(final Rendezvous rendezvous, final Comment comment) {
		final Rendezvous save = this.rendezvousService.findOne(rendezvous.getId());
		final ArrayList<Comment> comments = new ArrayList<>(save.getComment());
		comments.add(comment);
		save.setComment(comments);
		this.rendezvousService.save(save);
	}

	public void flush() {
		this.commentRepository.flush();
	}
}
