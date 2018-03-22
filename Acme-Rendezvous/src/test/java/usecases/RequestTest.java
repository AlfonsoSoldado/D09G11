package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.RequestService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Request;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class RequestTest extends AbstractTest {

	@Autowired
	private RequestService requestService;

	// Test---------------------------------------------------------------
	@Test
	public void requestTest() {
		final Object testingData[][] = {

				// An actor who is authenticated as a user must be able to:
				// 3. Request a service for one of the rendezvouses that he or
				// shes created. He or she
				// must specify a valid credit card in every request for a
				// service. Optionally, he or she
				// can provide some comments in the request.
				{
						// Listing requests.
						"user1", null }, {
						// Listing requests.
						"user2", null }, {
						// User edit a request.
						"user1", "a comment", "request1", null }, {
						// User edit a request.
						"user2", "another comment", "request2", null }, {
						// User edit the request of other user.
						"user1", "another comment", "request2", IllegalArgumentException.class }, {
						// User creates a request.
						"user1", "comment", null }, {
						// User creates a request.
						"user2", "comment", null }, {
						// User delete a request.
						"user1", "request1", null }, {
						// User delete a request.
						"user2", "request2", null } };

		for (int i = 0; i < 2; i++)
			this.listTemplate((String) testingData[i][0],
					(Class<?>) testingData[i][1]);

		for (int i = 2; i < 5; i++)
			this.editTemplate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);
//
//		for (int i = 5; i < 7; i++)
//			this.createTemplate((String) testingData[i][0],
//					(String) testingData[i][1], (Class<?>) testingData[i][2]);

		// for (int i = 7; i < testingData.length; i++)
		// this.deleteTemplate((String) testingData[i][0],
		// (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void createTemplate(final String user, final String comment,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------Create Request-------------------
			this.authenticate(user);

			CreditCard cc = new CreditCard();
			cc.setBrandName("MasterCard");
			cc.setCVV(334);
			cc.setExpirationMonth(12);
			cc.setExpirationYear(2019);
			cc.setHolderName("Raul");
			cc.setNumber("5574588374439106");

			Request request = requestService.create();
			request.setCreditCard(cc);
			request.setComment(comment);

			this.requestService.save(request);
			this.unauthenticate();
			this.requestService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void listTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------List Requests-------------------
			this.authenticate(user);
			this.requestService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void editTemplate(final String user, final String comment,
			final String request, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------Edit Request-------------------
			this.authenticate(user);
			final int requestId = this.getEntityId(request);
			final Request requestFinded = this.requestService
					.findOne(requestId);
			requestFinded.setComment(comment);
			this.requestService.save(requestFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void deleteTemplate(final String user, final String request,
			final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			// -----------------Delete Request-------------------
			this.authenticate(user);
			final int requestId = this.getEntityId(request);
			final Request requestFinded = this.requestService
					.findOne(requestId);
			this.requestService.delete(requestFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
