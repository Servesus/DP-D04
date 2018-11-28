package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Customer;

import security.UserAccount;
import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class CustomerServiceTest extends AbstractTest{
	
	//Service under test
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void createTest(){
		System.out.println("Entro en el create");
		
		Customer c =customerService.create();
		
		Assert.notNull(c);
		
		System.out.println("Se ha creado el customer y lo compruebo");
		System.out.println(c);
	}
	
	@Test
	public void saveTest(){
		System.out.println("Entro en el save");
		
		Customer c = customerService.create();
		
		System.out.println("Creo el customer");
		
		UserAccount userAccount= new UserAccount();
		userAccount.setUsername("customer20");
		userAccount.setPassword("123468023");
		c.setUserAccount(userAccount);
		c.setAddress("Calle Municipal");
		c.setEmail("miguel@gmail.com");
		c.setName("Miguel");
		c.setSurname("Velasco");
		c.setPhoneNumber("625817204");
		
		System.out.println("He puesto los atributos al customer");
		
		customerService.save(c);
		System.out.println("Se ha creado el customer y lo compruebo");
		
		customerService.findAll();
	}
	
	

}
