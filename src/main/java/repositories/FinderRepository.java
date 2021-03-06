
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

	@Query("select f from FixUpTask f where f.ticker like '%?1%' or f.description like '%?1%' or f.address like '%?1%'")
	Collection<FixUpTask> getFixUpTasksByKeyWord(String keyWord);

	@Query("select f from FixUpTask f where f.estimatedDate BETWEEN ?1 and ?2")
	Collection<FixUpTask> getFixUpTasksByDateRange(Date dateMin, Date dateMax);

	@Query("select f from FixUpTask f where f.maxPrice BETWEEN ?1 AND ?2")
	Collection<FixUpTask> getFixUpTasksByPriceRange(Integer minPrice, Integer maxPrice);

	@Query("select f from FixUpTask f where f.category.name like '%?1%'")
	Collection<FixUpTask> getFixUpTasksByCategory(String categoryName);

	@Query("select f from FixUpTask f where f.warranty.title like '%?1%'")
	Collection<FixUpTask> getFixUpTasksByWarranty(String warrantyTitle);

}
