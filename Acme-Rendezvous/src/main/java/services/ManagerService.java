package services;

import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Manager;
import forms.ManagerForm;

@Service
@Transactional
public class ManagerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private Validator validator;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public ManagerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Manager create() {
		Manager manager;
		manager = new Manager();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.MANAGER);
		userAccount.addAuthority(authority);
		manager.setUserAccount(userAccount);
		manager.setVAT(this.generatedVAT());
		return manager;
	}

	public Collection<Manager> findAll() {
		Collection<Manager> res;
		res = this.managerRepository.findAll();
		return res;
	}

	public Manager findOne(int manager) {
		Assert.isTrue(manager != 0);
		Manager res;
		res = this.managerRepository.findOne(manager);
		return res;
	}

	public Manager save(Manager manager) {
		Assert.notNull(manager);
		Manager res;
		if (manager.getId() == 0) {
			Class<?> caught;
			caught = null;
			try {
				LoginService.getPrincipal();
			} catch (final Throwable oops) {
				caught = oops.getClass();
			}
			this.checkExceptions(IllegalArgumentException.class, caught);
		}
		if (manager.getId() == 0) {
			String pass = manager.getUserAccount().getPassword();
			final Md5PasswordEncoder code = new Md5PasswordEncoder();
			pass = code.encodePassword(pass, null);
			manager.getUserAccount().setPassword(pass);
		}
		res = this.managerRepository.save(manager);
		return res;
	}

	public void delete(Manager manager) {
		Assert.notNull(manager);
		Assert.isTrue(manager.getId() != 0);
		Assert.isTrue(this.managerRepository.exists(manager.getId()));
		this.managerRepository.delete(manager);
	}

	// Other business method --------------------------------------------------

	public Manager findByPrincipal() {
		Manager manager;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		manager = this.managerRepository.findByPrincipal(userAccount.getId());
		return manager;
	}

	public void checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		Authority res = new Authority();
		res.setAuthority("MANAGER");
		Assert.isTrue(authority.contains(res));
	}
	
	public String generatedVAT() {
		String VAT;
		String letters;
		Random r;
		
		letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789-_/";
		r = new Random();
		VAT = String.valueOf(letters.charAt(r.nextInt(letters.length())));
		
		for (int i = 0; i < 8; i++)
			VAT = VAT + letters.charAt(r.nextInt(letters.length()));
		return VAT;
	}
	
	protected void checkExceptions(final Class<?> expected, final Class<?> caught) {
		if (expected != null && caught == null)
			throw new RuntimeException(expected.getName() + " was expected");
		else if (expected == null && caught != null)
			throw new RuntimeException(caught.getName() + " was unexpected");
		else if (expected != null && caught != null && !expected.equals(caught))
			throw new RuntimeException(expected.getName() + " was expected, but " + caught.getName() + " was thrown");
	}

	public ManagerForm reconstruct(final ManagerForm managerForm, final BindingResult binding) {
		Manager res;
		ManagerForm managerFinal = null;
		res = managerForm.getManager();
		if (res.getId() == 0) {
			UserAccount userAccount;
			Authority authority;
			String VAT = this.generatedVAT();
			userAccount = managerForm.getManager().getUserAccount();
			authority = new Authority();
			managerForm.getManager().setUserAccount(userAccount);
			authority.setAuthority(Authority.MANAGER);
			userAccount.addAuthority(authority);
			managerForm.getManager().setVAT(VAT);
			managerFinal = managerForm;
		} else {
			res = this.managerRepository.findOne(managerForm.getManager().getId());
			managerForm.getManager().setId(res.getId());
			managerForm.getManager().setVersion(res.getVersion());
			managerForm.getManager().setUserAccount(res.getUserAccount());
			managerForm.getManager().setVAT(res.getVAT());
			managerFinal = managerForm;
		}
		this.validator.validate(managerFinal, binding);
		return managerFinal;
	}

	public Manager reconstruct(final Manager manager, final BindingResult binding) {
		Manager res;
		Manager managerFinal;
		if (manager.getId() == 0) {
			UserAccount userAccount;
			Authority authority;
			String VAT = this.generatedVAT();
			userAccount = manager.getUserAccount();
			manager.setUserAccount(userAccount);
			authority = new Authority();
			authority.setAuthority(Authority.MANAGER);
			userAccount.addAuthority(authority);
			String password = "";
			password = manager.getUserAccount().getPassword();
			manager.getUserAccount().setPassword(password);
			manager.setVAT(VAT);
			managerFinal = manager;
		} else {
			res = this.managerRepository.findOne(manager.getId());
			manager.setId(res.getId());
			manager.setVersion(res.getVersion());
			manager.setUserAccount(res.getUserAccount());
			manager.getUserAccount().setPassword(manager.getUserAccount().getPassword());
			manager.getUserAccount().setAuthorities(manager.getUserAccount().getAuthorities());
			manager.setVAT(res.getVAT());
			managerFinal = manager;
		}
		this.validator.validate(managerFinal, binding);
		return managerFinal;
	}
	
	public void flush() {
		this.managerRepository.flush();
	}

}
