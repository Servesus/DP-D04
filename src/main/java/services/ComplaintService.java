
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;

	//Supporting services
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	public Complaint create() {
		final Complaint result = new Complaint();
		final Customer customer = this.customerService.create();
		final FixUpTask fixUpTask = this.fixUpTaskService.create();
		result.setTicker(CurriculaService.generadorDeTickers());
		result.setCustomer(customer);
		result.setFixUpTasks(fixUpTask);
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
		Assert.notNull(complaint);
		result = this.complaintRepository.save(complaint);
		return result;
	}

	public void delete(final Complaint complaint) {
		Assert.notNull(complaint);
		assert complaint.getId() != 0;
		Assert.isTrue(this.complaintRepository.exists(complaint.getId()));
		this.complaintRepository.delete(complaint);
	}
}