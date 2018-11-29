
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
	private ReportService	reportService;


	//Simple CRUD methods
	public Note create() {
		Note result;
		String authorName;
		final Collection<String> customerComments = new ArrayList<String>();
		final Collection<String> hwComments = new ArrayList<String>();
		final Collection<String> refereeComments = new ArrayList<String>();

		result = new Note();

		authorName = this.actorService.getActorLogged().getName() + this.actorService.getActorLogged().getMiddleName() + this.actorService.getActorLogged().getSurname();

		result.setAuthor(authorName);
		result.setCustomerComments(customerComments);
		result.setHwComments(hwComments);
		result.setRefereeComments(refereeComments);
		return result;
	}

	public List<Note> findAll() {
		return this.noteRepository.findAll();
	}

	public Note findOne(final Integer noteId) {
		Assert.isTrue(noteId != 0);
		return this.noteRepository.findOne(noteId);
	}

	public Note save(final Note n, final int reportId) {
		Assert.notNull(n);
		Assert.notNull(reportId);
		Note result;
		Report report;

		report = this.reportService.findOne(reportId);

		Date currentMoment;

		Collection<Note> notes = new ArrayList<Note>();

		notes = report.getNotes();

		currentMoment = new Date();
		n.setMoment(currentMoment);

		result = this.noteRepository.save(n);

		notes.add(result);
		notes.remove(n);
		report.setNotes(notes);

		this.reportService.save(report);

		return result;
	}

	public void delete(final Note n) {
		Assert.notNull(n);
		Collection<Report> reports = new ArrayList<Report>();

		reports = this.reportService.findAll();

		for (final Report r : reports)
			if (r.getNotes().contains(n)) {
				final Collection<Note> notes = r.getNotes();
				notes.remove(n);
				r.setNotes(notes);
				this.reportService.save(r);
			}

		this.noteRepository.delete(n);
	}
}
