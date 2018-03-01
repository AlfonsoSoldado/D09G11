
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------
	
	@Autowired
	private ActorRepository	actorRepository;

	// Constructor ------------------------------------------------------------
	
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		return result;
	}

	public Actor findOne(final int actorId) {
		Actor result;
		result = this.actorRepository.findOne(actorId);
		return result;
	}
	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		return this.actorRepository.save(actor);
	}

	// Other business method --------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final int id = userAccount.getId();
		result = this.actorRepository.findActorByUserAccount(id);
		return result;
	}

	public Actor findByUsername(final String username) {
		Assert.notNull(username);
		final Actor result = this.actorRepository.findByUsername(username);
		return result;
	}

	public boolean isAuthenticated() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount != null)
			result = true;
		return result;
	}

}
