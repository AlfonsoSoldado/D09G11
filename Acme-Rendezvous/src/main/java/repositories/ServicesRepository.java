
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;
import domain.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer> {

	
	@Query("select m from Services m where m.canceled=false")
	Collection<Services> servicesAviables();

	
	@Query("select m from Services m where m.manager.id=?1")
	Collection<Services> servicesByManager(int id);
	
	@Query("select s from Services s where s.rendezvous.id=?1")
	Collection<Services> servicesByRendezvous(int id);
	
	@Query("select r from Request r where r.services.id=?1")
	Request requestByServices(int id);
	
	
}
