package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Referee;

import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class RefereeServiceTest extends AbstractTest{
	
	@Autowired 
	private RefereeService refereeService;
	
	@Test
	public void createTest(){
		super.authenticate("admin1");
		System.out.println("Entro en el método");
		
		Referee r = refereeService.create();
		System.out.println("Comprobamos que no devuelve null y se crea");
		Assert.notNull(r);
		System.out.println("Se ha creado un referee");
		System.out.println(r);
	}
	
	@Test
	public void saveTest(){
		super.authenticate("admin1");
		UserAccount userAccount;
		
		System.out.println("Entro en el método");
		
		Referee r = refereeService.create();
		
		System.out.println("Se crea un referee con el create del servicio ya comprobado");
		
		userAccount = r.getUserAccount();
		userAccount.setUsername("referee300");
		userAccount.setPassword("123456789");
		
		r.setUserAccount(userAccount);
		
		System.out.println("Se crea una userAccount y se le pone un username y una password");
		
		r.setName("Sergio");
		r.setSurname("Pérez");
		r.setEmail("sergio@bananapps.com");
		r.setAddress("Mi calle");
		r.setPhoto("http://www.mifoto.com");
		r.setPhoneNumber("689312351");
		
		System.out.println("Le añadimos los atributos al referee");
		
		Referee referee = refereeService.save(r);
		
		System.out.println(refereeService.findOne(referee.getId()));
		System.out.println(refereeService.findAll());
		System.out.println("Comprobamos que se añade");
	}
}
