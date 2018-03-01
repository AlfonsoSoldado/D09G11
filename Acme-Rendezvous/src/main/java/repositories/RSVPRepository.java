
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RSVP;

@Repository
public interface RSVPRepository extends JpaRepository<RSVP, Integer> {

	@Query("select rs from User u join u.rsvp rs join rs.rendezvous ren where u.id=?1 and ren.id=?2 ")
	RSVP findRSVPByUserAndRendezvous(int userId, int rendezvousId);
}
