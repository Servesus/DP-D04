
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.FixUpTask;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from FixUpTask f where (f.ticker like '%?1%' or f.description like '%?1%' or f.address like '%?1%') and f.estimatedDate BETWEEN ?2 and ?3 and f.maxPrice BETWEEN ?4 and ?5 and f.category.name like '%?6%' and f.warranty.title like '%?7%'")
	Collection<FixUpTask> searchFixUpTasks(String keyWord, Date dateMin, Date dateMax, Double minPrice, Double maxPrice, String categoryName, String warrantyName);

}
