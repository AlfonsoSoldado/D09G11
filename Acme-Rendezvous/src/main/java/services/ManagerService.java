package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ManagerRepository managerRepository;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public ManagerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Manager create() {
		Manager manager;
		manager = new Manager();
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

}