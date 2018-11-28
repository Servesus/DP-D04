
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	private ComplaintService	complaintService;
	private CustomerService		customerService;
	private FixUpTaskService	fixUpTaskService;
	private RefereeService		refereeService;


	@Test
	public void testCreateComplaint() {

		final Complaint result = this.complaintService.create(2696);
		result.setDescription("");
		Assert.isTrue(!(result.equals(null)));

	}
	@Test
	public void testFindOneComplaint() {

		Integer id;
		id = 2715;
		final Complaint a = this.complaintService.findOne(id);
		Assert.isTrue(a.getDescription().equals("esta es la descripcion de complaint1"));

	}
	@Test
	public void testFindAllComplaint() {

		Collection<Complaint> busqueda = new ArrayList<Complaint>();
		busqueda = this.complaintService.findAll();
		Assert.isTrue(busqueda.size() == 5);

	}
	//	@Test
	//	public void testSaveComplaint() {
	//
	//		super.authenticate("customer1");
	//		final Complaint creado = this.complaintService.create(2696);
	//		creado.setDescription("");
	//		this.complaintService.save(creado);
	//		final Complaint prueba = this.complaintService.findOne(0);
	//		Assert.isTrue(creado.equals(prueba));
	//		super.authenticate(null);
	//	}
	//	@Test
	//	public void testDeleteComplaint() {
	//		super.authenticate("customer1");
	//		final Complaint caso1 = this.complaintService.findOne(2715);
	//		this.complaintService.delete(caso1);
	//		final Collection<Complaint> all = this.complaintService.findAll();
	//		Assert.isTrue(!(all.contains(caso1)));
	//		super.authenticate(null);
	//	}
	//	@Test
	//	public void testSelfAssignedComplaint() {
	//		super.authenticate("referee1");
	//		final Collection<Complaint> result = this.complaintService.getComplaintSelfAssigned();
	//		final Referee nuevo = this.refereeService.findOne(2684);
	//		final Collection<Complaint> res = new ArrayList<Complaint>();
	//		final Report[] apoyo = (Report[]) nuevo.getReports().toArray();
	//		for (int i = 0; i < apoyo.length; i++)
	//			res.add(apoyo[i].getComplaint());
	//		Assert.isTrue(result.containsAll(res));
	//		super.authenticate(null);
	//	}
}
