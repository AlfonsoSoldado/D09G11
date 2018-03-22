package usecases;

import java.util.Date;

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
import domain.Services;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class RequestTest extends AbstractTest {
	
	@Autowired
	private RequestService		requestService;
	
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
						// Manager creates a request.
						"manager1", "comment", "moment1", null, IllegalArgumentException.class },
				{
						// User creates a request.
						"user1", "comment", "moment1", null, null } };

//		for (int i = 0; i < 3; i++)
//			this.listTemplate((String) testingData[i][0],
//					(Class<?>) testingData[i][1]);
//
		for (int i = 3; i < 5; i++)
			this.createTemplate((String) testingData[i][0], (String) testingData[i][2],
					(Date) testingData[i][3], (Services) testingData[i][4], (Class<?>) testingData[i][6]);
//
//		// for (int i = 5; i < 8; i++)
//		// this.editTemplate((String) testingData[i][0], (String)
//		// testingData[i][1], (String) testingData[i][2], (Class<?>)
//		// testingData[i][3]);
//
//		for (int i = 8; i < testingData.length; i++)
//			this.deleteTemplate((String) testingData[i][0],
//					(String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	protected void createTemplate(final String user, final String comment, final Date moment, Services services, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Create Request-------------------
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
			request.setMoment(moment);

			this.requestService.save(request);
			this.unauthenticate();
			this.requestService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
