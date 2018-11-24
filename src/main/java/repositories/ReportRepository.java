
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	//The average of the number of notes per referee report
	@Query("select avg(r.notes.size) from Report r")
	Double getAvgNotes();

	//The maximum of the number of notes per referee report
	@Query("select max(r.notes.size) from Report r")
	Integer getMaxNotes();

	//The minimum of the number of notes per referee report
	@Query("select min(r.notes.size) from Report r")
	Integer getMinNotes();

	//The standard deviation of the number of notes per referee report
	@Query("select stddev(r.notes.size) from Report r")
	Double getSteddevNotes();
}
