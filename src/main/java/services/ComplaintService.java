
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;
import domain.Referee;
import domain.Report;

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
	@Autowired
	private RefereeService		refereeService;


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

	public List<Complaint> getComplaintSelfAssigned() {

		final Integer idReferee = this.actorService.getActorLogged().getId();
		final Referee referee = this.refereeService.findOne(idReferee);
		final List<Complaint> result = new ArrayList<Complaint>();
		final Report[] apoyo = (Report[]) referee.getReports().toArray();
		for (int i = 0; i < apoyo.length; i++)
			result.add(apoyo[i].getComplaint());
		return result;
	}

	public List<Complaint> getComplaintNoSelfAssigned() {

		final List<Complaint> result = this.complaintRepository.findAll();
		final List<Complaint> apoyo = this.getComplaintSelfAssigned();
		result.removeAll(apoyo);
		return result;
	}
}
