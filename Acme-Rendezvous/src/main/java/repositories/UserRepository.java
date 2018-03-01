
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select e from User e join e.userAccount ac where ac.id = ?1")
	User findByPrincipal(int id);

	@Query("select u from User u join u.rendezvous r where r.id=?1")
	User findCreator(int rendezvousId);

	@Query("select u from Rendezvous r join r.attendant u where r.id=?1")
	Collection<User> findAttendants(int rendezvousId);

	@Query("select u from User u join u.rsvp r join r.rendezvous rd where rd.id=?1 and r.confirmed=true")
	Collection<User> findUserRSVPbyRendezvous(int rendezvousId);
	
	@Query("select u from User u join u.question q where q.id=?1")
	User findUserByQuestion(int questionId);
	
	@Query("select u from User u join u.comment c where c.id=?1")
	User findUserByComment(int commentId);
	
	@Query("select u from User u join u.answer a where a.id=?1")
	User findUserByAnswer(int answerId);
}
