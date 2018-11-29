package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Application;
import domain.HandyWorker;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class ApplicationServiceTest extends AbstractTest{
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Test
	public void createTest(){
		super.authenticate("handyWorker1");
		System.out.println("Entro en el método");
		int futId = this.getEntityId("fixUpTask1");
		Application a = applicationService.create(futId);
		Assert.notNull(a);
		System.out.println("Compruebo que no devuelva un null");
	}
	
	@Test
	public void saveTestHandyWorker(){
		super.authenticate("handyWorker1");
		System.out.println("Entro en el método");
		int fixUpTaskId = this.getEntityId("fixUpTask1");
		
		Application result = applicationService.create(fixUpTaskId);
		System.out.println("Creo una Application nueva");
		
		Application a = applicationService.save(result);
		System.out.println("Guardo la application");
		Assert.notNull(applicationService.findOne(a.getId()));
		System.out.println(applicationService.findOne(a.getId()));
		System.out.println("Compruebo que se guarda en la fixUpTask");
		System.out.println(fixUpTaskService.findOne(fixUpTaskId).getApplications());
		
	}
	
	@Test
	public void saveTestCustomer(){
		super.authenticate("customer1");
		System.out.println("Entro en el método");
		int applicationId = this.getEntityId("application1");
		
		Application a = applicationService.findOne(applicationId);
		HandyWorker hw = a.getHandyWorker();
		a.setStatus(-1);
		System.out.println("Hago los cambios y guardo la application");
		Application result = applicationService.save(a);
		System.out.println(result.getStatus());
		System.out.println(hw.getApplications().iterator().next().getStatus());
	}
}
