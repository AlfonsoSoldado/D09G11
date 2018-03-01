
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	@Query("select q from Question q join q.rendezvous r where r.id=?1")
	Collection<Question> findQuestionByRendezvous(int rendezvousId);

	@Query("select distinct(q) from User s join s.answer a join a.question q join q.rendezvous r where s.id=?1 and r.id=?2")
	Collection<Question> findQuestionAnswered(int userId, int rendezvousId);

}
