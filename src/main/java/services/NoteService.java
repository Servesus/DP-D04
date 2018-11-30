
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
<<<<<<< HEAD
=======
import security.UserAccount;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
>>>>>>> origin/miguel
import domain.Note;
import domain.Report;

@Service
@Transactional
public class NoteService {

	//Managed Repository
	@Autowired
	private NoteRepository	noteRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
<<<<<<< HEAD
	private ReportService	reportService;
=======
	private ReportService reportService;
	
	@Autowired
	private ComplaintService complaintService;
>>>>>>> origin/miguel


	//Simple CRUD methods
	public Note create(int complaintId) {
		UserAccount userAccount;
		Note result;
<<<<<<< HEAD
		String authorName;
		final Collection<String> customerComments = new ArrayList<String>();
		final Collection<String> hwComments = new ArrayList<String>();
		final Collection<String> refereeComments = new ArrayList<String>();

		result = new Note();

		authorName = this.actorService.getActorLogged().getName() + this.actorService.getActorLogged().getMiddleName() + this.actorService.getActorLogged().getSurname();

		result.setAuthor(authorName);
=======
		Collection<String> customerComments= new ArrayList<String>();
		Collection<String> hwComments= new ArrayList<String>();
		Collection<String> refereeComments= new ArrayList<String>();
		Date moment;
		Customer customer = complaintService.findOne(complaintId).getCustomer();
		HandyWorker handyWorker= null;
		Collection<Application> apps = complaintService.findOne(complaintId)
				.getFixUpTasks().getApplications();
		
		for(int i =0;i<apps.size();i++){
			Application iterador= apps.iterator().next();
			if(iterador.getStatus()==1){
				handyWorker= iterador.getHandyWorker();
				}
			}

		userAccount = this.actorService.getActorLogged().getUserAccount();

		Assert.isTrue(userAccount.equals(customer.getUserAccount()) || 
				userAccount.equals(handyWorker.getUserAccount()));
		
		result = new Note();
		moment = new Date();
		
>>>>>>> origin/miguel
		result.setCustomerComments(customerComments);
		result.setHwComments(hwComments);
		result.setRefereeComments(refereeComments);
		result.setMoment(moment);
		return result;
	}

	public List<Note> findAll() {
		return this.noteRepository.findAll();
	}

	public Note findOne(final Integer noteId) {
		Assert.isTrue(noteId != 0);
		return this.noteRepository.findOne(noteId);
	}

<<<<<<< HEAD
	public Note save(final Note n, final int reportId) {
=======
	public Note save(final Note n, int complaintId) {
>>>>>>> origin/miguel
		Assert.notNull(n);
		Note result;
<<<<<<< HEAD
		Report report;

		report = this.reportService.findOne(reportId);

		Date currentMoment;

		Collection<Note> notes = new ArrayList<Note>();

		notes = report.getNotes();

		currentMoment = new Date();
		n.setMoment(currentMoment);

=======
		UserAccount userAccount;
		Complaint c = complaintService.findOne(complaintId);
		Report r= null;
		Collection<Report> reports = reportService.findAll();
		
		for(int i =0;i<reports.size();i++){
			Report iterador= reports.iterator().next();
			if(iterador.getComplaint().equals(c)){
				r= iterador;
				}
			}
		
		FixUpTask f = c.getFixUpTasks();
		Collection<Application> apps = f.getApplications();
		HandyWorker handyWorker=null;
		Customer customer = c.getCustomer();
		
		noteRepository.findOne(n.getId());
		for(int i =0;i<apps.size();i++){
		Application iterador= apps.iterator().next();
		if(iterador.getStatus()==1){
			handyWorker= iterador.getHandyWorker();
			}
		}
		
		userAccount = this.actorService.getActorLogged().getUserAccount();
		
		Assert.isTrue(userAccount.equals(customer.getUserAccount()) || 
				userAccount.equals(handyWorker.getUserAccount()));
		
		Collection<Note> notes = new ArrayList<Note>();
		
		notes= r.getNotes();

		
>>>>>>> origin/miguel
		result = this.noteRepository.save(n);

		notes.add(result);
		notes.remove(n);
<<<<<<< HEAD
		report.setNotes(notes);

		this.reportService.save(report);
=======
		r.setNotes(notes);
		
		reportService.save(r);
>>>>>>> origin/miguel

		return result;
	}

	public void delete(final Note n) {
		Assert.notNull(n);
<<<<<<< HEAD
		Collection<Report> reports = new ArrayList<Report>();

		reports = this.reportService.findAll();

		for (final Report r : reports)
			if (r.getNotes().contains(n)) {
				final Collection<Note> notes = r.getNotes();
=======
		Collection<Report> reports= new ArrayList<Report>();
		reports= reportService.findAll();
		
		for(Report r : reports){
			if(r.getNotes().contains(n)){
				Collection<Note> notes = r.getNotes();
>>>>>>> origin/miguel
				notes.remove(n);
				r.setNotes(notes);
				this.reportService.save(r);
			}

		this.noteRepository.delete(n);
	}
}
