package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

import domain.Note;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class NoteServiceTest extends AbstractTest{
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private ReportService reportService;
	
	@Test
	public void createTest(){
		System.out.println("Entro en el método");
		Note n = noteService.create();
		Assert.notNull(n);
		System.out.println("Compruebo que no devuelva un null");
	}
	
	@Test
	public void saveTest(){
		super.authenticate("customer1");
		int reportId = this.getEntityId("report1");
		System.out.println("Entro en el método");
		Note note = noteService.create();
		
		note.setAuthorComment("Comentario del autor");
		
		System.out.println("Añado el comentario del autor a la nota nueva");
		System.out.println("Solo debería estar vacío los comentarios de hw, customer y referee");
		
		Note n = noteService.save(note, reportId);
		
		System.out.println(noteService.findOne(n.getId()));
		System.out.println(noteService.findOne(n.getId()).getRefereeComments());
		System.out.println("Deberia salir la nota bien y que se añada en el report");
		System.out.println(reportService.findOne(reportId).getNotes());
	}
	
	@Test
	public void saveTest2(){
		super.authenticate("customer1");
		int noteId= this.getEntityId("note1");
		int reportId= this.getEntityId("report1");
		Collection<String> customerComments;
		
		System.out.println("Entro en el método");
		
		Note note = noteService.findOne(noteId);
		
		System.out.println("Cojo la nota para editarla y le añado comentarios en la collection");
		System.out.println("de customerComments");
		System.out.println("Cojo los customerComments que ya estaba en la nota");
		
		customerComments= note.getCustomerComments();
		customerComments.add("Nuevo comentario del customer");
		
		note.setCustomerComments(customerComments);
		
		Note n = noteService.save(note, reportId);
		
		System.out.println("Guardo la nota y compruebo que se haya guardado en la nota y el report");
		System.out.println(noteService.findOne(n.getId()));
		System.out.println(noteService.findOne(n.getId()).getCustomerComments());
		System.out.println("Deberia salir la nota bien y que se añada en el report");
		System.out.println(reportService.findOne(reportId).getNotes().iterator().next()
				.getCustomerComments());
	}
	
	@Test
	public void deleteTest(){
		super.authenticate("customer1");
		int noteId = this.getEntityId("note1");
		int reportId = this.getEntityId("report1");
		
		System.out.println("Entro en el método");
		
		Note note = noteService.findOne(noteId);
		Report r  = reportService.findOne(reportId);
		
		noteService.delete(note);
		System.out.println("Borro la nota y compruebro que el findOne devuelva null");
		Assert.isNull(noteService.findOne(noteId));
		
		System.out.println("Compruebo que se haya borrado del report");
		System.out.println(r.getNotes());
	}

}
