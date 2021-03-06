package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;
import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
	
	@Query("select r from Rendezvous rv join rv.requests r where rv.id=?1")
	Collection<Request> findRequestByRendezvous(int id);
	
	@Query("select r from User u join u.rendezvous rv join rv.requests r where u.id=?1")
	Collection<Request> findRequestByUser(int id);
	
	@Query("select r.creditCard from User u join u.rendezvous rv join rv.requests r where u.id=?1 order by r.moment DESC")
	Collection<CreditCard> findAllCreditCard(int id);
	
}
