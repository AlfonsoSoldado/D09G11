
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.name=?1")
	Actor findByUsername(String username);

	@Query("select a from Actor a join a.userAccount ac where ac.id = ?1")
	Actor findActorByUserAccount(int id);

	@Query("select a from Actor a where a.id=?1")
	Actor findByOne(int id);
}
