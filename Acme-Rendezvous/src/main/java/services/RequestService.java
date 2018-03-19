package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.CreditCard;
import domain.Rendezvous;
import domain.Request;
import domain.Services;
import domain.User;

@Service
@Transactional
public class RequestService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequestRepository requestRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RendezvousService rendezvousService;
	
	@Autowired
	private UserService userService;

	// Constructor ------------------------------------------------------------

	public RequestService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Request create() {
		User user = userService.findByPrincipal();
		final Request request;
		Services services = new Services();
		Collection<CreditCard> creditCards = new ArrayList<CreditCard>();
		request = new Request();
		creditCards = this.findAllCreditCard(user.getId());
		request.setServices(services);
		if (creditCards.size() > 0)
			request.setCreditCard(creditCards.iterator().next());
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
		if (request.getId() == 0) {
			Date moment;
			moment = new Date(System.currentTimeMillis() - 1000);
			request.setMoment(moment);
		}
		res = this.requestRepository.save(request);
		
		Rendezvous rendezvous;
		rendezvous = this.rendezvousService.findRendezvousByRequest(res);
		Assert.isTrue(rendezvous.getServices() == null);
		
		return res;
	}

	public Request save(Request request, int rendezvousId) {
		Assert.notNull(request);
		Request res;
		if (request.getId() == 0) {
			Date moment;
			moment = new Date(System.currentTimeMillis() - 1000);
			request.setMoment(moment);
		}
		res = this.requestRepository.save(request);

		Rendezvous r = rendezvousService.findOne(rendezvousId);

		Collection<Request> requests = new ArrayList<Request>();

		requests = r.getRequests();
		requests.add(res);

		r.setRequests(requests);

		return res;
	}

	public void delete(Request request) {
		Assert.notNull(request);
		Assert.isTrue(request.getId() != 0);
		Assert.isTrue(this.requestRepository.exists(request.getId()));
		this.requestRepository.delete(request);
	}

	// Other business method --------------------------------------------------

	public Collection<Request> findRequestByRendezvous(int rendezvousId) {
		Collection<Request> requests = new ArrayList<Request>();

		requests = requestRepository.findRequestByRendezvous(rendezvousId);

		return requests;
	}

	public Collection<Request> findRequestByUser(int userId) {
		Collection<Request> requests = new ArrayList<Request>();
		
		requests = requestRepository.findRequestByUser(userId);
		
		return requests;
	}
	
	public Collection<CreditCard> findAllCreditCard(int userId) {
		Collection<CreditCard> creditcCards = new ArrayList<CreditCard>();
		
		creditcCards = requestRepository.findAllCreditCard(userId);
		
		return creditcCards;
	}
}
