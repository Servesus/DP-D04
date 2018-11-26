
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;

	//Supporting services
	@Autowired
	private FixUpTaskService	fixUpTaskService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private ActorService		actorService;


	public Complaint create(final Integer idFixUpTask) {
		final Complaint result = new Complaint();
		result.setTicker(CurriculaService.generadorDeTickers());
		final Date moment = new Date();
		final Integer idCustomer = this.actorService.getActorLogged().getId();
		result.setMoment(moment);
		result.setFixUpTasks(this.fixUpTaskService.findOne(idFixUpTask));
		result.setCustomer(this.customerService.findOne(idCustomer));
		return result;
	}

	public Collection<Complaint> findAll() {
		Collection<Complaint> result;
		Assert.notNull(this.complaintRepository);
		result = this.complaintRepository.findAll();
		return result;
	}

	public Complaint findOne(final int complaintId) {
		Complaint result;
		Assert.notNull(this.complaintRepository);
		result = this.complaintRepository.findOne(complaintId);
		return result;

	}

	public Complaint save(final Complaint complaint) {

		Complaint result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("CUSTOMER"));
		Assert.notNull(complaint);
		result = this.complaintRepository.save(complaint);
		return result;
	}

	public void delete(final Complaint complaint) {

		Assert.notNull(complaint);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("CUSTOMER"));
		assert complaint.getId() != 0;
		Assert.isTrue(this.complaintRepository.exists(complaint.getId()));
		this.complaintRepository.delete(complaint);
	}
}
