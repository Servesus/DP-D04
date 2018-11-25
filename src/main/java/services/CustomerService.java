package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Customer;

import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;

@Service
@Transactional
public class CustomerService {
	
	//Managed repositories
	@Autowired
	private CustomerRepository customerRepository;
	
	//Supporting repositories
	@Autowired
	private FixUpTaskRepository fixUpTaskRepository;
	
	public Customer create() {
		Customer result;
		Authority auth;
		UserAccount userAccount;
		Collection<Authority> authorities;
		
		result = new Customer();
		userAccount = new UserAccount();
		auth= new Authority();
		authorities= new ArrayList<Authority>();
		
		
		auth.setAuthority(Authority.CUSTOMER);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		
		result.setUserAccount(userAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);

		return result;
	}
	
	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = customerRepository.findAll();
		Assert.notNull(result);

		return result;
	}
	
	public Customer findOne(int customerId) {
		Assert.isTrue(customerId != 0);
		Customer result;

		result = customerRepository.findOne(customerId);
		Assert.notNull(result);

		return result;
	}
	
	public Customer save(Customer customer) {
		Assert.notNull(customer);

		Customer result;

		result = customerRepository.save(customer);

		return result;
	}
	
	public void delete(Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() != 0);

		customerRepository.delete(customer);
	}
	
	//TODO Hay que hacer que pueda mostrar fixUpTasks y eso, hay que esperar a que Sergio lo haga
	
	//TODO Lo mismo pero con complaint, hay que esperar a Sergio

}
