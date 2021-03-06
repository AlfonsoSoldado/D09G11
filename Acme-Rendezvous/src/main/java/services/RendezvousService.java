
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RendezvousRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Announcement;
import domain.Comment;
import domain.Rendezvous;
import domain.Request;
import domain.User;

@Service
@Transactional
public class RendezvousService {

	// Managed repository ----------------------------------------------------

	@Autowired
	private RendezvousRepository	rendezvousRepository;

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService				userService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;


	// Constructor ------------------------------------------------------------

	public RendezvousService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Rendezvous create() {
		this.userService.checkAuthority();
		final Rendezvous res = new Rendezvous();
		res.setComment(new ArrayList<Comment>());
		res.setAttendant(new ArrayList<User>());
		res.setSimilar(new ArrayList<Rendezvous>());
		res.setAnnouncement(new ArrayList<Announcement>());
		res.setRequests(new ArrayList<Request>());
		res.setDeleted(false);
		return res;
	}

	public Collection<Rendezvous> findAll() {
		Collection<Rendezvous> res;
		res = this.rendezvousRepository.findAll();
		return res;
	}

	public Rendezvous findOne(final int rendezvousId) {
		Assert.isTrue(rendezvousId != 0);
		Rendezvous res;
		res = this.rendezvousRepository.findOne(rendezvousId);
		return res;
	}

	public Rendezvous save(final Rendezvous rendezvous) {
		this.checkAuthorityUser();
		Assert.notNull(rendezvous);
		Authority admin = new Authority();
		admin.setAuthority("ADMIN");
		if (rendezvous.getId() != 0 && !this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(admin)) {
			Assert.isTrue(this.userService.findCreator(rendezvous.getId()) == this.userService.findByPrincipal());
			Assert.isTrue(this.findOne(rendezvous.getId()).getFinalMode() == false);
		}
		Rendezvous res;
		res = this.rendezvousRepository.save(rendezvous);
		return res;
	}

	public void delete(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getId() != 0);
		Assert.isTrue(this.rendezvousRepository.exists(rendezvous.getId()));
		Assert.isTrue(this.actorService.isAuthenticated());
		if (this.checkAuthority() == false)
			Assert.isTrue(this.userService.findCreator(rendezvous.getId()) == this.userService.findByPrincipal());
		rendezvous.setFinalMode(true);
		rendezvous.setDeleted(true);
		this.rendezvousRepository.save(rendezvous);
	}
	// Other business method --------------------------------------------------

	public Rendezvous findRendezvousByComment(final int commentId) {
		Rendezvous res;
		res = this.rendezvousRepository.findRendezvousByComment(commentId);
		return res;
	}

	public Collection<Rendezvous> findByAttendantId(final int attendantId) {
		Collection<Rendezvous> res;
		res = this.rendezvousRepository.findByAttendantId(attendantId);
		return res;
	}

	public Collection<Rendezvous> findByCreator(final int creatorId) {
		Collection<Rendezvous> res;
		res = this.rendezvousRepository.findByCreator(creatorId);
		return res;
	}

	public Rendezvous findRendezvousByQuestionId(final int questionId) {
		Rendezvous res;
		res = this.rendezvousRepository.findRendezvousByQuestionId(questionId);
		return res;
	}

	public Collection<Rendezvous> findRendezvousAdultOnly() {
		Collection<Rendezvous> res;
		res = this.rendezvousRepository.findRendezvousAdultOnly();
		return res;
	}

	public Collection<Rendezvous> findNotYetAttendantByUserId(final int userId) {

		Collection<Rendezvous> res;
		res = this.rendezvousRepository.findNotYetAttendantByUserId(userId);
		return res;
	}

	public Rendezvous reconstruct(final Rendezvous rendezvous, final BindingResult binding) {
		Rendezvous res;
		Rendezvous rendezvousFinal;
		if (rendezvous.getId() == 0) {
			Collection<Comment> comment;
			Collection<User> attendant;
			Collection<Announcement> announcement;
			announcement = new ArrayList<Announcement>();
			attendant = new ArrayList<User>();
			comment = new ArrayList<Comment>();
			rendezvous.setAttendant(attendant);
			rendezvous.setAnnouncement(announcement);
			rendezvous.setDeleted(false);
			rendezvous.setComment(comment);
			res = rendezvous;
		} else {
			rendezvousFinal = this.rendezvousRepository.findOne(rendezvous.getId());
			rendezvous.setAttendant(rendezvousFinal.getAttendant());
			rendezvous.setAnnouncement(rendezvousFinal.getAnnouncement());
			rendezvous.setComment(rendezvousFinal.getComment());
			res = rendezvous;
		}
		this.validator.validate(res, binding);
		return res;
	}

	public Collection<Rendezvous> findRendezvousNotCancelled() {
		return this.rendezvousRepository.findRendezvousNotCancelled();
	}

	public Collection<Rendezvous> findRendezvousWithServices() {
		return this.rendezvousRepository.findRendezvousWithServices();
	}

	public Rendezvous findRendezvousByServices(final int servicesId) {
		return this.rendezvousRepository.findRendezvousByServices(servicesId);
	}

	public Rendezvous findRendezvousByRequest(final Request request) {
		return this.rendezvousRepository.findRendezvousByRequest(request);
	}

	
	public void checkAuthorityUser() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		Authority res = new Authority();
		res.setAuthority("USER");
		Authority admin = new Authority();
		admin.setAuthority("ADMIN");
		Assert.isTrue(authority.contains(res)||authority.contains(admin));
	}
	public boolean checkAuthority() {
		boolean ret;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("ADMIN");
		if (authority.contains(res))
			ret = true;
		else
			ret = false;
		return ret;
	}
	
	public void flush() {
		this.rendezvousRepository.flush();
	}
}
