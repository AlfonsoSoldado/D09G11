
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ServicesRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Manager;
import domain.Rendezvous;
import domain.Request;
import domain.Services;

@Service
@Transactional
public class ServicesService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ServicesRepository	servicesRepository;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private RequestService		requestService;


	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public ServicesService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Services create() {
		Services services;
		services = new Services();

		Rendezvous r;
		r = new Rendezvous();
		Collection<Category> category;
		category = new ArrayList<Category>();

		final Manager manager = this.managerService.findByPrincipal();

		services.setRendezvous(r);
		services.setManager(manager);
		services.setCanceled(false);
		services.setCategory(category);

		return services;
	}

	public Collection<Services> findAll() {
		Assert.notNull(LoginService.getPrincipal());
		Collection<Services> res;
		res = this.servicesRepository.findAll();
		return res;
	}
	public Services findOne(final int services) {
		Assert.isTrue(services != 0);
		Services res;
		res = this.servicesRepository.findOne(services);
		res.setLevel(this.updateLevel(res));

		return res;
	}

	public Services save(final Services services) {
		Assert.notNull(services);
		this.checkAuthority();

		Services res;

		final Rendezvous r = services.getRendezvous();

		services.setLevel(this.updateLevel(services));
		res = this.servicesRepository.save(services);

		if (services.getId() == 0) {
			services.setCanceled(false);

			r.setServices(res);
		} else {
			Assert.isTrue(services.getManager() == this.managerService.findByPrincipal());
			Request request;
			request = this.requestByServices(services.getId());
			request.setServices(services);
			this.requestService.save(request);
		}

		return res;
	}

	private Integer updateLevel(final Services services) {
		Integer res = 5;
		for (final Category category : services.getCategory())
			if (category.getLevel() < res)
				res = category.getLevel();
		if (res == 5)
			res = null;
		return res;
	}

	public void delete(final Services services) {
		Assert.notNull(services);
		Assert.isTrue(services.getId() != 0);
		Assert.isTrue(this.servicesRepository.exists(services.getId()));
		this.checkAuthority();
		Assert.isTrue(services.getManager() == this.managerService.findByPrincipal());

		Request request;
		request = this.requestByServices(services.getId());
		request.setServices(null);

		Rendezvous rendezvous;
		rendezvous = this.rendezvousService.findRendezvousByServices(services.getId());

		rendezvous.setServices(null);

		Collection<Category> category = new ArrayList<Category>();
		category = this.categoryService.findCategoryByServices(services.getId());

		for (final Category c : category) {
			Collection<Services> ss = new ArrayList<Services>();
			ss = c.getServices();
			ss.remove(services);

			c.setServices(ss);
		}

		this.servicesRepository.delete(services);
	}

	// Other business method --------------------------------------------------

	public Collection<Services> servicesAviables() {
		return this.servicesRepository.servicesAviables();
	}

	public Collection<Services> servicesByManager(final Manager manager) {
		this.managerService.checkAuthority();
		return this.servicesRepository.servicesByManager(manager.getId());
	}

	public Services reconstruct(final Services services, final BindingResult binding) {
		Services res;
		Services serviceFinal;
		if (services.getId() == 0)
			res = services;
		else {
			serviceFinal = this.findOne(services.getId());
			services.setManager((Manager) this.actorService.findByPrincipal());
			serviceFinal.setRendezvous(services.getRendezvous());
			serviceFinal.setCategory(services.getCategory());
			res = serviceFinal;
		}

		return res;
	}

	public Collection<Services> ServicesByRendezvous(final int rendezvousId) {
		Collection<Services> res = new ArrayList<Services>();
		res = this.servicesRepository.servicesByRendezvous(rendezvousId);
		return res;
	}

	public Request requestByServices(final int servicesId) {
		return this.servicesRepository.requestByServices(servicesId);
	}

	public void checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("MANAGER");
		Assert.isTrue(authority.contains(res));
	}

	public void flush() {
		this.servicesRepository.flush();
	}
}
