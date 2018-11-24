package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Customer;

import repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	
	//Managed repositories
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer create() {
		Customer result;

		result = new Customer();

		return result;
	}
	
	public Collection<Customer> findAll() {
		Collection<Customer> result;

		result = customerRepository.findAll();
		Assert.notNull(result);

		return result;
	}
	
	public Customer findOne(int customerId) {
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

}
