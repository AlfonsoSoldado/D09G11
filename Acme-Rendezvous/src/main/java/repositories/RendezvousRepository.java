
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rendezvous;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous, Integer> {

	@Query("select r from Rendezvous r join r.comment c where c.id=?1")
	Rendezvous findRendezvousByComment(int id);

	@Query("select m from Rendezvous m join m.attendant e where e.id=?1")
	Collection<Rendezvous> findByAttendantId(int userid);

	@Query("select m from Rendezvous m join m.similar e where e.id=?1")
	Collection<Rendezvous> findRendezvousContainThisAsSimilar(int rendezvousId);

	@Query("select r from User u join u.rendezvous r where u.id=?1")
	Collection<Rendezvous> findByCreator(int userid);

	@Query("select r from Question q join q.rendezvous r where q.id=?1")
	Rendezvous findRendezvousByQuestionId(int questionId);

	@Query("select r from Rendezvous r where r.adultOnly=true")
	Collection<Rendezvous> findRendezvousAdultOnly();

	@Query("select r from Rendezvous r where r.deleted = false and r not in (select m from Rendezvous m join m.attendant e where e.id=?1)")
	Collection<Rendezvous> findNotYetAttendantByUserId(int userId);

}
