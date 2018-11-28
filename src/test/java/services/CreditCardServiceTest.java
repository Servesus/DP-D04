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

import security.UserAccount;
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
		Customer c = customerService.create();
		
		UserAccount userAccount = c.getUserAccount();
		
		userAccount.setUsername("juanjo");
		userAccount.setPassword("1234567");
		
		c.setUserAccount(userAccount);
		
		customerService.save(c);
		
		super.authenticate("juanjo");
		System.out.println("Entro en el método");
		CreditCard creditCard= creditCardService.create();
		System.out.println("CreditCard creada");
		Assert.notNull(creditCard);
		
	}

}
