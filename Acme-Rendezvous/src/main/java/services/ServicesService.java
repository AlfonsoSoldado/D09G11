package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ServicesRepository;
import domain.Services;

@Service
@Transactional
public class ServicesService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ServicesRepository servicesRepository;

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

	public Services save(Services services) {
		Assert.notNull(services);
		Services res;
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

}
