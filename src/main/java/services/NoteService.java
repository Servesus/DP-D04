
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Note;

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

	public Note save(final Note n) {
		Assert.notNull(n);
		Note result;
		Date currentMoment;
		String authorName;
		Collection<Note> notes;
		
		authorName= actorService.getActorLogged().getName() + 
				actorService.getActorLogged().getMiddleName() +
				actorService.getActorLogged().getSurname();

		currentMoment = new Date();
		n.setMoment(currentMoment);
		n.setAuthor(authorName);
		
		
		result = this.noteRepository.save(n);

		return result;
	}

	//TODO
	public void delete(final Note n) {
		Assert.notNull(n);
		this.noteRepository.delete(n);
	}
}
