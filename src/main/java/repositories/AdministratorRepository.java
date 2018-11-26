
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//The average of the number of applications per fix-up task.
	@Query("select avg(f.applications.size) from FixUpTask f")
	Double getAvgApplications();

	//The maximum of the number of applications per fix-up task.
	@Query("selec max(f.applications.size) from FixUpTask f")
	Integer getMaxApplications();

	//The minimum of the number of applications per fix-up task.
	@Query("selec min(f.applications.size) from FixUpTask f")
	Integer getMinApplications();

	//The standard deviation of the number of applications per fix-up task.
	@Query("selec stddev(f.applications.size) from FixUpTask f")
	Integer getStddevApplications();

	//The average of the maximum price per fix-up task.
	@Query("select avg(f.maxPrice) from FixUpTask f")
	Double getAvgMaxPrice();

	//The maximum of the maximum price per fix-up task.
	@Query("selec max(f.maxPrice) from FixUpTask f")
	Integer getMaxMaxPrice();

	//The minimum of the maximum price per fix-up task.
	@Query("selec min(f.maxPrice) from FixUpTask f")
	Integer getMinMaxPrice();

	//The standard deviation of the maximum price per fix-up task.
	@Query("selec stddev(f.maxPrice) from FixUpTask f")
	Integer getStddevMaxPrice();

	//The average of the complaints per fix-up task.
	@Query("select avg(f.complaints.size) from FixUpTask f")
	Double getAvgComplaints();

	//The maximum of complaints per fix-up task.
	@Query("selec max(f.complaints.size) from FixUpTask f")
	Integer getMaxComplaints();

	//The minimum of the complaints per fix-up task.
	@Query("selec min(f.complaints.size) from FixUpTask f")
	Integer getMinComplaints();

	//The standard deviation of the complaints per fix-up task.
	@Query("selec stddev(f.complaints.size) from FixUpTask f")
	Integer getStddevComplaints();

	// The ratio of fix-up tasks with a complaint.
	@Query("select 100.0*(select count(f) from FixUpTask f where f.complaints.size > 0)/count(f) from FixUpTask f")
	Double getRatio();
}
