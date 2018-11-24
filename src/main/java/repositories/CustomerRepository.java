package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Customer;

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

	//The top-three customers in terms of complaints.
	@Query("select c.name from Customer c join c.complaints com group by c.id order by com.size DESC")
	Collection<Customer> getTopCustomersByComplaints();
}
