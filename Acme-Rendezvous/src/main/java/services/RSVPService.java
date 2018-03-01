
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RSVPRepository;
import domain.RSVP;

@Service
@Transactional
public class RSVPService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RSVPRepository	rsvpRepository;

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService		userService;

	@Autowired
	private QuestionService	questionService;


	// Constructor ------------------------------------------------------------

	public RSVPService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public RSVP create() {
		this.userService.checkAuthority();
		RSVP result;
		result = new RSVP();
		return result;
	}

	public Collection<RSVP> findAll() {
		Collection<RSVP> result;
		result = this.rsvpRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public RSVP findOne(final int rsvpId) {
		RSVP result;
		result = this.rsvpRepository.findOne(rsvpId);
		return result;
	}

	public RSVP save(final RSVP rsvp) {
		this.userService.checkAuthority();
		RSVP result = rsvp;
		Assert.notNull(rsvp);
		Assert.isTrue(rsvp.getConfirmed() == true);
		result = this.rsvpRepository.save(result);
		return result;
	}

	public void delete(final RSVP rsvp) {
		Assert.notNull(rsvp);
		Assert.isTrue(rsvp.getId() != 0);
		this.rsvpRepository.delete(rsvp);
	}

	// Other business method --------------------------------------------------

	public RSVP findRSVPByUserAndRendezvous(final int userId, final int rendezvousId) {
		final RSVP result = this.rsvpRepository.findRSVPByUserAndRendezvous(userId, rendezvousId);
		return result;
	}

	public boolean questionsAnswered(final int rendezvousId, final int userId) {

		Boolean res = false;

		final Integer q = this.questionService.findQuestionByRendezvous(rendezvousId).size();
		final Integer qa = this.questionService.findQuestionAnswered(userId, rendezvousId).size();

		if (q == qa)
			res = true;

		return res;

	}
}
