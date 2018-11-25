
package services;

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

		currentMoment = new Date();
		n.setMoment(currentMoment);
		result = this.noteRepository.save(n);

		return result;
	}

	//TODO
	public void delete(final Note n) {
		Assert.notNull(n);
		this.noteRepository.delete(n);
	}
}
