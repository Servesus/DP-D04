
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
import domain.Actor;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class NoteService {

	//Managed Repository
	@Autowired
	private NoteRepository	noteRepository;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ReportService reportService;


	//Simple CRUD methods
	public Note create() {
		Note result;
		result = new Note();
		return result;
	}

	public List<Note> findAll() {
		return this.noteRepository.findAll();
	}

	public Note findOne(final Integer noteId) {
		Assert.isTrue(noteId != 0);
		return this.noteRepository.findOne(noteId);
	}

	public Note save(final Note n, int reportId) {
		Assert.notNull(n);
		Note result;
		Report report;
		
		report= reportService.findOne(reportId);
		
		Date currentMoment;
		String authorName;
		Collection<Note> notes = new ArrayList<Note>();
		
		notes= report.getNotes();
		
		authorName= actorService.getActorLogged().getName() + 
				actorService.getActorLogged().getMiddleName() +
				actorService.getActorLogged().getSurname();

		currentMoment = new Date();
		n.setMoment(currentMoment);
		n.setAuthor(authorName);
		
		result = this.noteRepository.save(n);
		
		notes.add(result);
		
		reportService.save(report);

		return result;
	}

	public void delete(final Note n) {
		Assert.notNull(n);
		Collection<Report> reports= new ArrayList<Report>();
		String authorName;
		String actorName;
		Actor actor;
		
		actor= actorService.getActorLogged();
		actorName= actor.getName()+ actor.getSurname()+ actor.getSurname();
		
		authorName= n.getAuthor();
		
		Assert.isTrue(authorName.equals(actorName));
		
		reports= reportService.findAll();
		
		for(Report r : reports){
			if(r.getNotes().contains(n)){
				Collection<Note> notes = r.getNotes();
				notes.remove(n);
				r.setNotes(notes);
				reportService.save(r);
			}
		}
		
		this.noteRepository.delete(n);
	}
}
