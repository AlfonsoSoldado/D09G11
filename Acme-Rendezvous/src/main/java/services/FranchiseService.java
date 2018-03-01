package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FranchiseRepository;
import domain.Franchise;

@Service
@Transactional
public class FranchiseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FranchiseRepository franchiseRepository;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public FranchiseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Franchise create() {
		Franchise franchise;
		franchise = new Franchise();
		return franchise;
	}

	public Collection<Franchise> findAll() {
		Collection<Franchise> res;
		res = this.franchiseRepository.findAll();
		return res;
	}

	public Franchise findOne(int franchise) {
		Assert.isTrue(franchise != 0);
		Franchise res;
		res = this.franchiseRepository.findOne(franchise);
		return res;
	}

	public Franchise save(Franchise franchise) {
		Assert.notNull(franchise);
		Franchise res;
		res = this.franchiseRepository.save(franchise);
		return res;
	}

	public void delete(Franchise franchise) {
		Assert.notNull(franchise);
		Assert.isTrue(franchise.getId() != 0);
		Assert.isTrue(this.franchiseRepository.exists(franchise.getId()));
		this.franchiseRepository.delete(franchise);
	}

	// Other business method --------------------------------------------------

}
