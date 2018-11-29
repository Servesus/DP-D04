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
		Customer c =customerService.create();
		
		Assert.notNull(c);
	}
	
	@Test
	public void saveTest(){
		Customer c = customerService.create();
		
		UserAccount userAccount= c.getUserAccount();
		userAccount.setUsername("customer20");
		userAccount.setPassword("123468023");
		c.setUserAccount(userAccount);
		c.setAddress("Calle Municipal");
		c.setEmail("miguel@gmail.com");
		c.setName("Miguel");
		c.setSurname("Velasco");
		c.setPhoneNumber("625817204");
		
		Customer test = customerService.save(c);
		
		Assert.isTrue(customerService.findAll().contains(test));
	}

}
