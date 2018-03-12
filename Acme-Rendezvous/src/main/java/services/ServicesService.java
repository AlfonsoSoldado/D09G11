package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ServicesRepository;
import domain.Manager;
import domain.Services;

@Service
@Transactional
public class ServicesService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ServicesRepository servicesRepository;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private ActorService actorService;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public ServicesService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Services create() {
		Services services;
		services = new Services();
		return services;
	}

	public Collection<Services> findAll() {
		Collection<Services> res;
		res = this.servicesRepository.findAll();
		return res;
	}

	public Services findOne(int services) {
		Assert.isTrue(services != 0);
		Services res;
		res = this.servicesRepository.findOne(services);
		return res;
	}

	//TODO
	public Services save(Services services) {
		Assert.notNull(services);
		Services res;
		if (services.getId() != 0) {
			Services oldServices = this.findOne(services.getId());

			// ---------

		}

		res = this.servicesRepository.save(services);
		return res;
	}

	public void delete(Services services) {
		Assert.notNull(services);
		Assert.isTrue(services.getId() != 0);
		Assert.isTrue(this.servicesRepository.exists(services.getId()));
		this.servicesRepository.delete(services);
	}

	// Other business method --------------------------------------------------

	public Collection<Services> servicesAviables() {
		return this.servicesRepository.servicesAviables();
	}

	public Collection<Services> servicesByManager(Manager manager) {
		this.managerService.checkAuthority();
		return this.servicesRepository.servicesByManager(manager.getId());
	}

	public Services reconstruct(Services services, BindingResult binding) {
		Services res;
		Services serviceFinal;
		if (services.getId() == 0) {
			res = services;
		} else {
			serviceFinal = this.findOne(services.getId());
			services.setManager((Manager) this.actorService.findByPrincipal());
			services.setRendezvous(serviceFinal.getRendezvous());
			services.setCategory(serviceFinal.getCategory());
			res = services;
		}

		return res;
	}
	
	public Collection<Services> ServicesByRendezvous(int rendezvousId){
		Collection<Services> res = new ArrayList<Services>();
		res = servicesRepository.servicesByRendezvous(rendezvousId);
		return res;
	}

}
