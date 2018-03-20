
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Manager;
import domain.Rendezvous;
import domain.Services;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select avg(m.rendezvous.size*1.0) from User m")
	double averageRedezvousUser();

	@Query("select  stddev(m.rendezvous.size*1.0) from User m")
	double EstandardDesviationRedezvousUser();

	@Query("select (select count(a) from User a where a.rendezvous.size>0)/count(ap)*1.0 from User ap")
	double ratioUserConRendezvous();

	@Query("select (select count(b) from User b where b.rendezvous.size=0)/count(c)*1.0 from User c")
	double ratioUserSinRendezvous();

	@Query("select avg(m.attendant.size*1.0) from Rendezvous m")
	double averageUsersRendezvous();

	@Query("select stddev(m.attendant.size*1.0) from Rendezvous m")
	double estandardDesviationUsersRendezvous();

	@Query("select avg(m.rsvp.size*1.0) from User m join m.rsvp e where e.confirmed=true")
	double averageRendezvousRSVPTruePerUser();

	@Query("select stddev(m.rsvp.size*1.0) from User m join m.rsvp e where e.confirmed=true")
	double estandardDesviationRendezvousRSVPTruePerUser();

	@Query("select r from Rendezvous r where r.attendant.size!=0 order by r.attendant.size desc")
	Page<Rendezvous> topRendezvous(Pageable a);

	@Query("select avg(e.announcement.size*1.0) from Rendezvous e")
	double averageAnnouncementsRendezvous();

	@Query("select stddev(e.announcement.size*1.0) from Rendezvous e")
	double estandardDesviationAnnouncementsUser();

	@Query("select m from Rendezvous m where m.similar.size > (select avg(v.similar.size)*1.1 from Rendezvous v)")
	Collection<Rendezvous> redezvousSimiliars10();

	@Query("select avg(m.question.size*1.0/m.rendezvous.size) from User m")
	double averageNumberOfQuestionsPerRendezvous();

	@Query("select stddev(m.question.size*1.0/m.rendezvous.size) from User m")
	double estandardDesviationOfQuestionsPerRendezvous();

	// revisar
	@Query("select count(m.answer.size)/(select count(f) from Rendezvous f)*1.0 from Question m")
	double averageOfAnswerPerQuestionsPerRendezvous();

	// revisar
	@Query("select stddev((m.answer.size*1.0)/(select count(f) from Rendezvous f)*1.0) from Question m")
	double estandardDesviationOfAnswerPerQuestionsPerRendezvous();

	@Query("select r from Rendezvous r where r.announcement.size >(select avg(n.announcement.size)*0.75 from Rendezvous n)")
	Collection<Rendezvous> RendezvousConMas075Announcement();

	@Query("select avg(m.replies.size) from Comment m")
	double averageRepliesComment();

	@Query("select stddev(m.replies.size) from Comment m")
	double estandardDesviationRepliesComment();

	@Query("select e from Administrator e join e.userAccount ac where ac.id = ?1")
	Administrator findByPrincipal(int id);

	// consultas nuevas
	// consultas del C
	@Query("select c.services from Category c where c.services.size=(select max(m.services.size) from Category m)")
	Collection<Services> bestSellingServices();

	// estas dos consultas forman una sola
	@Query("select m.manager from Services m where (select  count(s) from Services s where s.manager=m.manager)>=?1")
	Collection<Manager> managersWhoPprovideMoreServicesThanTheAverage(long media);

	@Query("select  count(s) from Services s group by s.manager")
	Collection<Long> servicesPerManagerAndManaer();

	@Query("select  count(s), s.manager from Services s where s.canceled=true group by s.manager order by count(s) desc")
	List<Object[]> managersWhoHaveGotMoreServicesCancelled();
	// select s from Services s group by s.manager; servicios agrupados por manager
	// select m.manager from Services m where (select count(s) from Services s where
	// s.manager=m.manager)>=1.1//managers con mas servicios que la mesdia

	// consultas del B
	@Query("select avg(s.category.size) from Services s")
	Double averageOfCategoriesPerRendezvous();

	@Query("select avg(c.services.size/(select count(m) from Category m)) from Category c")
	Double averageRatioServicesInEachCategory();

	@Query("select avg(r.requests.size) from Rendezvous r")
	Double averageServicesRequestedPerRendezvous();

	@Query("select min(r.requests.size) from Rendezvous r")
	Double minServicesRequestedPerRendezvous();

	@Query("select max(r.requests.size) from Rendezvous r")
	Double maxServicesRequestedPerRendezvous();

	@Query("select stddev(r.requests.size) from Rendezvous r")
	Double standardDesviationServicesRequestedPerRendezvous();

	@Query("select c.services from Category c order by c.services.size desc")
	Collection<Services> topSellingServices();
}
