package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	//The average, the minimum, the maximum, 
	//and the standard deviation of the number of fix-up tasks per user:
	
	@Query("select avg(c.fixUpTasks.size) from Customer c")
	Double getAvgOfFixUpTasks();
	
	@Query("select min(c.fixUpTasks.size) from Customer c")
	Integer getMinOfFixUpTasks();
	
	@Query("select max(c.fixUpTasks.size) from Customer c")
	Integer getMaxOfFixUpTasks();
	
	@Query("select stddev(c.fixUpTasks.size) from Customer c")
	Double getStddevOfFixUpTasks();
	
	//A customer must be able to: Manage an arbitrary number of fix-up tasks,
	//which includes listing, showing, creating, updating, and deleting them
	@Query("select f from Customer c join c.fixUpTasks f where c.userAccount.id=?1")
	List<FixUpTask> getFixUpTasks(int userAccountId);
	
	//Manage the applications for his or her fix-up tasks, which includes listing and updating
	//them
	@Query("select a from Customer c join c.fixUpTasks f join f.applications a where c.userAccount.id=?2")
	List<Application> getApplications(int userAccountId);
	
	//Manage his or her complaints, which includes listing, showing, and creating them
	@Query("select com from Customer c join c.complaints com where c.userAccount.id=?3")
	List<Complaint> getComplaints(int userAccountId);
}
