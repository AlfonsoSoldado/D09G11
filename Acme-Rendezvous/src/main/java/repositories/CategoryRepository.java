
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select c from Category c join c.services s where s.id=?1")
	Collection<Category> findCategoryByServices(int id);
	
	@Query("select c from Category c where c.level=?1")
	Collection<Category> findCategoryByLevel(int id);

}
