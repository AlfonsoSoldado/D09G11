
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Franchise;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Integer> {

}
