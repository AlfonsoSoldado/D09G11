
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnnouncementRepository announcementRepository;

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdministratorService administratorService;

	// Constructor ------------------------------------------------------------

	public AnnouncementService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Announcement create(final Rendezvous rendezvous) {
		userService.checkAuthority();
		Announcement result;
		result = new Announcement();
		result.setRendezvous(rendezvous);
		return result;
	}

	public Collection<Announcement> findAll() {
		Collection<Announcement> result;
		result = this.announcementRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Announcement findOne(final int announcementId) {
		Announcement result;
		result = this.announcementRepository.findOne(announcementId);
		return result;
	}

	public Announcement save(final Announcement announcement) {
		userService.checkAuthority();
		Announcement result = announcement;
		Assert.notNull(announcement);
		if (announcement.getId() == 0) {
			Date momentMade;
			momentMade = new Date(System.currentTimeMillis() - 1000);
			result.setMomentMade(momentMade);
			final Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
			User user;
			user = this.userService.findByPrincipal();
			rendezvouses.addAll(user.getRendezvous());
			Assert.isTrue(rendezvouses.contains(announcement.getRendezvous()));
		}
		result = this.announcementRepository.save(result);
		return result;
	}

	public void delete(final Announcement announcement) {
		administratorService.checkAuthority();
		Assert.notNull(announcement);
		Assert.isTrue(announcement.getId() != 0);
		Rendezvous rendezvous;
		rendezvous = announcement.getRendezvous();
		rendezvous.getAnnouncement().remove(announcement);
		this.announcementRepository.delete(announcement);
	}

	public void deleteAll(final Collection<Announcement> announcements) {
		Assert.notNull(announcements);
		this.announcementRepository.delete(announcements);
	}

	// Other business method --------------------------------------------------

	public Collection<Announcement> findAnnouncementsByRendezvous(final int id) {
		Collection<Announcement> res = new ArrayList<Announcement>();
		res.addAll(this.announcementRepository.findAnnouncementsByRendezvous(id));
		res = this.announcementRepository.findAnnouncementsByRendezvous(id);
		Assert.notNull(res);
		return res;
	}

	public Collection<Announcement> findAnnouncementsByUser(final int userId) {
		final Collection<Announcement> res = this.announcementRepository.findAnnouncementsByUser(userId);
		return res;
	}

}
