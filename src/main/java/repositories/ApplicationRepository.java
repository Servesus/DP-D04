package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	
	//The average, the minimum, the maximum, and the standard deviation
	//of the price offered in the applications:

	@Query("select avg(a.price) from Application a")
	Double getAvg();
	
	@Query("select min(a.price) from Application a")
	Double getMin();
	
	@Query("select max(a.price) from Application a")
	Double getMax();
	
	@Query("select stddev(a.price) from Application a")
	Double getStddev();

	//The ratio of pending applications:
	
	@Query("select 100.0*(select count(a) from Application a where a.status = 0)/count(a) from Application a")
	Double getRatioOfPendingApplications();
	
	//The ratio of accepted applications:
	@Query("select 100.0*(select count(a) from Application a where a.status = 1)/count(a) from Application a")
	Double getRatioOfAcceptedApplications();
	
	//The ratio of rejected applications:
	@Query("select 100.0*(select count(a) from Application a where a.status = -1)/count(a) from Application a")
	Double getRatioOfRejectedApplications();
	
	//The ratio of pending applications that cannot change its status 
	//because their time period has elapsed
	@Query("select 100.0*(select count(a) from Application a  where (a.status = 0) and (a.fixUpTasks.startDate < CURRENT_TIMESTAMP()))/count(a) from Application a")
	Double getRatioOfPendingApplicationsCanNotChangeStatus();
}
