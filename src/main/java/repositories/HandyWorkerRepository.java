
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	//Listing of handy workers who have got accepted at least 10% more ap-plications than the average, ordered by number of applications
	@Query("select h from HandyWorker h join h.applications a where" + "((select count(a) from HandyWorker h where a.status=1 and a.handyWorker=h)/" + "(h.applications.size)>=(select 1.1*(select count(h2) "
		+ "from HandyWorker h2 join h2.applications a2 where (a2.status=1))" + "/count(a) from HandyWorker a)) order by a.size desc;")
	List<HandyWorker> hWMoreAcceptedThanAvg();

	//top-three handy workers in terms of complaints
	@Query("select h.name from HandyWorker h join h.applications a join a.fixUpTasks t group by h.id order by t.complaints.size DESC;")
	List<HandyWorker> top3HandyWorker();
}
