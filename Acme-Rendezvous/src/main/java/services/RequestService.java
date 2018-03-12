package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Request;
import domain.Services;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequestRepository requestRepository;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public RequestService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Request create() {
		final Request request;
		Services services = new Services();
		
		request = new Request();
		request.setServices(services);
		return request;
	}

	public Collection<Request> findAll() {
		Collection<Request> res;
		res = this.requestRepository.findAll();
		return res;
	}

	public Request findOne(int request) {
		Assert.isTrue(request != 0);
		Request res;
		res = this.requestRepository.findOne(request);
		return res;
	}

	public Request save(Request request) {
		Assert.notNull(request);
		Request res;
		res = this.requestRepository.save(request);
		return res;
	}

	public void delete(Request request) {
		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);
		Assert.isTrue(this.requestRepository.exists(request.getId()));
		this.requestRepository.delete(request);
	}

	// Other business method --------------------------------------------------

}
