
package usecases;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CategoryService;
import services.RendezvousService;
import utilities.AbstractTest;
import domain.Category;
import domain.Rendezvous;
import domain.Services;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RendezvousTest extends AbstractTest {

	@Autowired
	private RendezvousService	rendezvousService;
	
	@Autowired
	private CategoryService categoryService;


	// Test---------------------------------------------------------------
	@Test
	public void rendezvousTest() {
		final Object testingData[][] = {

			//An actor who is not authenticated must be able to:
			//List the rendezvouses in the system 

			//An actor who is authenticated as a user must be able to:
			//1.List the rendezvouses in the system 
			//2.Create a rendezvous, which he is implicitly assumed to attend. Note that a user may
			//edit his or her rendezvouses as long as they are not saved them in final mode. Once a
			//rendezvous is saved in final mode, it cannot be edited or deleted by the creator.
			//3.Update or delete the rendezvouses that he or she is created. Deletion is virtual, that
			//is: the information is not removed from the database, but the rendezvous cannot be
			//updated. Deleted rendezvouses are flagged as such when they are displayed.
			//4. List the rendezvouses that he or she is RSVPd.
			//5. Link one of the rendezvouses that he or she is created to other similar rendezvouses. 

			//An actor who is authenticated as an administrator must be able to:
			//Remove a rendezvous that he or she thinks is inappropriate. 

			{
				//Not authenticated actor list rendezvous.
				null, null
			}, {
				//User list rendezvous.
				"user1", null
			}, {
				//User not created list rendezvous.
				"user3", IllegalArgumentException.class
			}, {
				//Not authenticated actor list rendezvous by category.
				null, "category1", null
			}, {
				//User list rendezvous by category.
				"user1", "category1", null
			}, {
				//User not created list rendezvous by category.
				"user3", "category1", IllegalArgumentException.class
			}, {
				//Not authenticated actor list similar rendezvous.
				null, "rendezvous1", null
			}, {
				//User list similar rendezvous.
				"user1", "rendezvous1", null
			}, {
				//User not created list similar rendezvous.
				"user3", "rendezvous1", IllegalArgumentException.class
			}, {
				//User creates rendezvous.
				"user2", "name", "description", "2021/05/04 22:00", false, false, null
			}, {
				//User creates rendezvous.
				"user1", "name2", "description2", "2021/07/06 12:00", false, true, null
			}, {
				//User creates rendezvous.
				"user2", "name3", "description3", "2021/09/07 21:00", true, false, null
			}, {
				//User creates rendezvous.
				"user1", "name4", "description4", "2021/11/12 11:00", true, true, null
			}, {
				//Not authenticated actor creates rendezvous.
				null, "name", "description", "2021/05/04 22:00", false, false, IllegalArgumentException.class
			}, {
				//User edit a rendezvous in no finalMode that he or she has created.
				"user2", "edited name", "rendezvous2", null
			}, {
				//User edit a rendezvous in finalMode that he or she has created.
				"user1", "edited name", "rendezvous1", IllegalArgumentException.class
			}, {
				//User edit a rendezvous that he or she has not created.
				"user1", "edited name", "rendezvous2", IllegalArgumentException.class
			}, {
				//Not authenticated actor edit a rendezvous.
				null, "edited name", "rendezvous2", IllegalArgumentException.class
			}, {
				//Admin delete a rendezvous 
				"admin", "rendezvous2", null
			}, {
				//User delete a rendezvous that he or she has created.
				"user2", "rendezvous2", null
			}, {
				//User delete a rendezvous that he or she has not created.
				"user1", "rendezvous2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < 3; i++)
			this.listTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
		
		for (int i = 3; i < 6; i++)
			this.listByCategoryTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
		
		for (int i = 6; i < 9; i++)
			this.listSimilarTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

		for (int i = 9; i < 14; i++)
			this.createTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (boolean) testingData[i][4], (boolean) testingData[i][5], (Class<?>) testingData[i][6]);

		for (int i = 14; i < 18; i++)
			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

		for (int i = 18; i < testingData.length; i++)
			this.deleteTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteTemplate(final String user, final String rendezvous, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Delete Rendezvous-------------------
			this.authenticate(user);
			final int rendezvousId = this.getEntityId(rendezvous);
			final Rendezvous rendezvousFinded = this.rendezvousService.findOne(rendezvousId);
			this.rendezvousService.delete(rendezvousFinded);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void editTemplate(final String user, final String name, final String rendezvous, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Edit Rendezvous-------------------
			this.authenticate(user);
			final int rendezvousId = this.getEntityId(rendezvous);
			final Rendezvous rendezvousFinded = this.rendezvousService.findOne(rendezvousId);
			rendezvousFinded.setName(name);
			this.rendezvousService.save(rendezvousFinded);
			this.unauthenticate();
			this.rendezvousService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void createTemplate(final String user, final String name, final String description, final String date, final boolean finalMode, final boolean adultOnly, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------Create Rendezvous-------------------
			this.authenticate(user);
			final Rendezvous rendezvous = this.rendezvousService.create();
			rendezvous.setName(name);
			rendezvous.setDescription(description);
			final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			rendezvous.setMoment(format.parse(date));
			rendezvous.setFinalMode(finalMode);
			rendezvous.setAdultOnly(adultOnly);

			this.rendezvousService.save(rendezvous);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void listTemplate(final String user, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------List Rendezvous-------------------
			this.authenticate(user);
			this.rendezvousService.findAll();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	protected void listByCategoryTemplate(final String user, final String category, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------List Rendezvous-------------------
			this.authenticate(user);
			final int categoryId = this.getEntityId(category);
			final Category categoryFinded = this.categoryService.findOne(categoryId);
			
			for(Services s: categoryFinded.getServices()){
				rendezvousService.findRendezvousByServices(s.getId());
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	protected void listSimilarTemplate(final String user, final String rendezvous, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			//-----------------List Rendezvous-------------------
			this.authenticate(user);
			final int rendezvousId = this.getEntityId(rendezvous);
			final Rendezvous rendezvousFinded = this.rendezvousService.findOne(rendezvousId);
			rendezvousFinded.getSimilar();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
