package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Customer;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class CreditCardServiceTest extends AbstractTest{
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void createTest(){
		super.authenticate("customer1");
		System.out.println("Entro en el método");
		CreditCard creditCard= creditCardService.create();
		System.out.println("CreditCard creada");
		Assert.notNull(creditCard);
		
	}
	
	@Test
	public void saveTest(){
		super.authenticate("customer1");
		int id = this.getEntityId("customer1");
		
		System.out.println("Entro en el save");
		CreditCard c = creditCardService.create();
		
		c.setHolderName("holderNameTest");
		c.setBrandName("VISA");
		c.setNumber("4609805199761841");
		c.setExpirationYear(2020);
		c.setMonth(12);
		c.setCvv(893);
		
		creditCardService.save(c);
		
		Customer customer= customerService.findOne(id);
		System.out.println("Guardo la creditCard");
		System.out.println("Compruebo que se añade la creditCard");
		System.out.println(customer.getCreditCards());
	}
	
	@Test
	public void deleteTest(){
		super.authenticate("customer1");
		int id = this.getEntityId("creditCard1");
		CreditCard c = creditCardService.findOne(id);
		int idCustomer = this.getEntityId("customer1");
		
		System.out.println("Entro en el delete");
		
		Customer customer= customerService.findOne(idCustomer);
		
		creditCardService.delete(c);
		
		System.out.println("Borro la creditCard");
		System.out.println("Compruebo que se borra del customer");
		System.out.println(customer.getCreditCards());
	}

}
