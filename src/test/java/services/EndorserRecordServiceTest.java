package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.EndorserRecord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest{

	@Autowired
	private EndorserRecordService endorserRecordService;
	
	@Test
	public void createTest(){
		super.authenticate("handyWorker1");
		
		System.out.println("Entro en el método");
		
		EndorserRecord er = endorserRecordService.create();
		
		System.out.println("Se crea un endorserRecord vacío y lo comprobamos");
		System.out.println(er);
	}
	
	@Test
	public void saveTest(){
		super.authenticate("handyWorker1");
		
		System.out.println("Entro en el método");
	}
}
