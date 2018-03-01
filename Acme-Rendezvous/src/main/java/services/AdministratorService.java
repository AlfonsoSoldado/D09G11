package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Rendezvous;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository administratorRepository;

	// Constructor ------------------------------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator create() {
		Administrator result;
		result = new Administrator();
		return result;
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> result;
		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator findOne(final int administratorId) {
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		return result;
	}

	public Administrator save(final Administrator administrator) {
		Administrator result = administrator;
		Assert.notNull(administrator);
		result = this.administratorRepository.save(result);
		return result;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		this.administratorRepository.delete(administrator);
	}

	// Other business method --------------------------------------------------

	public double averageRedezvousUser() {
		return this.administratorRepository.averageRedezvousUser();
	}

	public double estandardDesviationRedezvousUser() {
		return this.administratorRepository.EstandardDesviationRedezvousUser();
	}

	public double ratioUserConRendezvous() {
		return this.administratorRepository.ratioUserConRendezvous();
	}

	public double ratioUserSinRendezvous() {
		return this.administratorRepository.ratioUserSinRendezvous();

	}

	public double averageUsersRendezvous() {
		return this.administratorRepository.averageUsersRendezvous();
	}

	public double estandardDesviationUsersRendezvous() {
		return this.administratorRepository.estandardDesviationUsersRendezvous();
	}

	public double averageRendezvousRSVPTruePerUser() {
		return this.administratorRepository.averageRendezvousRSVPTruePerUser();

	}

	public double estandardDesviationRendezvousRSVPTruePerUser() {
		return this.administratorRepository.estandardDesviationRendezvousRSVPTruePerUser();
	}

	public Collection<Rendezvous> topRendezvous() {
		Collection<Rendezvous> res;
		final Page<Rendezvous> pages;
		final Pageable pageable;
		pageable = new PageRequest(0, 10);
		pages = this.administratorRepository.topRendezvous(pageable);
		res = pages.getContent();
		return res;
	}

	public double averageannouncementsRendezvous() {
		return this.administratorRepository.averageAnnouncementsRendezvous();
	}

	public double estandardDesviationAnnouncementsUser() {
		return this.administratorRepository.estandardDesviationAnnouncementsUser();
	}

	public Collection<Rendezvous> redezvousSimiliars10() {
		final Collection<Rendezvous> res = new ArrayList<>();
		if (!this.administratorRepository.redezvousSimiliars10().isEmpty()
				&& this.administratorRepository.redezvousSimiliars10() != null)
			res.addAll(this.administratorRepository.redezvousSimiliars10());
		return res;
	}

	public double averageNumberOfQuestionsPerRendezvous() {
		return this.administratorRepository.averageNumberOfQuestionsPerRendezvous();
	}

	public double estandardDesviationOfQuestionsPerRendezvous() {
		return this.administratorRepository.estandardDesviationOfQuestionsPerRendezvous();
	}

	public double averageOfAnswerPerQuestionsPerRendezvous() {
		return this.administratorRepository.averageOfAnswerPerQuestionsPerRendezvous();
	}

	public double estandardDesviationOfAnswerPerQuestionsPerRendezvous() {
		return this.administratorRepository.estandardDesviationOfAnswerPerQuestionsPerRendezvous();
	}

	public Collection<Rendezvous> RendezvousConMas075Announcement() {
		return this.administratorRepository.RendezvousConMas075Announcement();
	}

	public double averageRepliesComment() {
		return this.administratorRepository.averageRepliesComment();
	}

	public double estandardDesviationRepliesComment() {
		return this.administratorRepository.estandardDesviationRepliesComment();
	}

	public Administrator findByPrincipal() {
		Administrator e;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		e = this.administratorRepository.findByPrincipal(userAccount.getId());
		return e;
	}

	public void checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		Authority res = new Authority();
		res.setAuthority("ADMIN");
		Assert.isTrue(authority.contains(res));
	}

	public boolean checkAdminLogged() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("ADMIN");
		if (authority.contains(res))
			result = true;
		return result;
	}
}
