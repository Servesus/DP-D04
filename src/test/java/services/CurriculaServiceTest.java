
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;
import domain.Curricula;
import domain.FixUpTask;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private PersonalRecordService		personalRecordService;
	@Autowired
	private EducationalRecordService	educationalRecordService;
	@Autowired
	private EndorserRecordService		endorserRecordService;
	@Autowired
	private ProfessionalRecordService	professionalRecordService;
	@Autowired
	private MiscRecordService			miscRecordService;
	@Autowired
	private ActorService				actorService;
	@Autowired
	private HandyWorkerService			handyWorkerService;


	@Test
	public void testCreateCurricula() {

		final Curricula result = this.curriculaService.create();
		Assert.isTrue(result.getTicker() != null || result.getPersonalRecord() != null || result.getEducationalRecord() != null || result.getEducationalRecord().isEmpty() || result.getEndorserRecord() != null || result.getEndorserRecord().isEmpty()
			|| result.getEndorserRecord() != null || result.getEndorserRecord().isEmpty() || result.getProfessionalRecord() != null || result.getProfessionalRecord().isEmpty() || result.getMiscRecord() != null || result.getMiscRecord().isEmpty());

	}
	@Test
	public void testFindOneCurricula() {

		final PersonalRecord personalRecord1 = this.personalRecordService.findOne(this.getEntityId("personalRecord1"));
		final PersonalRecord educationalRecord1 = this.educationalRecordService.findOne(this.getEntityId("personalRecord1"));
		final PersonalRecord personalRecord1 = this.personalRecordService.findOne(this.getEntityId("personalRecord1"));
		final PersonalRecord personalRecord1 = this.personalRecordService.findOne(this.getEntityId("personalRecord1"));
		final Curricula result = this.curriculaService.findOne(this.getEntityId("curricula1"));
		Assert.isTrue(result.getTicker() == "111118-e63ty6" && result.getPersona() == "esta es la descripcion de complaint1" && result.getCustomer().equals(ejemplo1) && result.getFixUpTasks().equals(ejemplo));

	}
	@Test
	public void testFindAllCurricula() {

		Integer comparador = 0;
		final Complaint[] metodo = (Complaint[]) this.complaintService.findAll().toArray();
		final FixUpTask[] apoyo = (FixUpTask[]) this.fixUpTaskService.findAll().toArray();
		for (int i = 0; i < apoyo.length; i++)
			comparador += apoyo[i].getComplaints().size();
		Assert.isTrue(metodo.length == comparador);

	}
	@Test
	public void testSaveCurricula() {

		super.authenticate("customer1");
		final Complaint creado = this.complaintService.create(2696);
		creado.setDescription("");
		this.complaintService.save(creado);
		final Complaint prueba = this.complaintService.findOne(0);
		Assert.isTrue(creado.equals(prueba));
		super.authenticate(null);
	}
	@Test
	public void testDeleteCurricula() {
		super.authenticate("customer1");
		final Complaint caso1 = this.complaintService.findOne(2715);
		this.complaintService.delete(caso1);
		final Collection<Complaint> all = this.complaintService.findAll();
		Assert.isTrue(!(all.contains(caso1)));
		super.authenticate(null);
	}

}
