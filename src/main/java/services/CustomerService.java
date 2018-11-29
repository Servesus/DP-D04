package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Application;
import domain.Box;
import domain.Complaint;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.Profile;

import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;

@Service
@Transactional
public class CustomerService {
	
	//Managed repositories
	@Autowired
	private CustomerRepository customerRepository;
	
	//Supporting services
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ComplaintService complaintService;
	
	public Customer create() {
		Customer result;
		Authority auth;
		UserAccount userAccount;
		Collection<Authority> authorities;
		Collection<Profile> profiles;
		Collection<Box> boxes;
		Collection<CreditCard> creditCards;
		Collection<Complaint> complaints;
		Collection<FixUpTask> fixUpTasks;
		
		result = new Customer();
		userAccount = new UserAccount();
		auth= new Authority();
		authorities= new ArrayList<Authority>();
		
		profiles= new ArrayList<Profile>();
		boxes= new ArrayList<Box>();
		creditCards= new ArrayList<CreditCard>();
		complaints= new ArrayList<Complaint>();
		fixUpTasks= new ArrayList<FixUpTask>();
		
		auth.setAuthority(Authority.CUSTOMER);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		
		result.setUserAccount(userAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);
		result.setProfiles(profiles);
		result.setBoxes(boxes);
		result.setCreditCards(creditCards);
		result.setComplaints(complaints);
		result.setFixUpTasks(fixUpTasks);

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

		return result;
	}
	
	public Customer save(Customer customer) {
		Assert.notNull(customer);

		Customer result;
		
		if(customer.getId()==0){
			Collection<Box> systemBox = boxService.createSystemBoxes();
			customer.setBoxes(systemBox);
		}

		result = customerRepository.save(customer);

		return result;
	}
	
	public List<FixUpTask> showFixUpTasks(){
		Actor actor;
		List <FixUpTask> result;
		
		actor = actorService.getActorLogged();
		int userAccountId= actor.getUserAccount().getId();
		
		result= customerRepository.getFixUpTasks(userAccountId);
		
		return result; 
	}
	
	public FixUpTask getFixUpTask(int fixUpTaskId){
		FixUpTask result;
		List<FixUpTask> fixUpTasks;
		List<Integer> ids = new ArrayList<Integer>();
		int i= 0;
		
		fixUpTasks= showFixUpTasks();
		
		while(i<fixUpTasks.size()){
			ids.add(fixUpTasks.get(i).getId());
			i++;
		}
		
		Assert.isTrue(ids.contains(fixUpTaskId));
		
		result= fixUpTaskService.findOne(fixUpTaskId);
		
		return result;
	}
	
	public Customer getCustomerLogged(){
		Actor actor;
		Customer result;
		
		actor= actorService.getActorLogged();
		
		result= findOne(actor.getId());
		
		return result;
	}
	
	public List<Application> showApplications(){
		List<Application> result;
		Customer customer;
		
		customer= getCustomerLogged();
		
		result= customerRepository.getApplications(customer.getUserAccount().getId());
		
		return result;
	}
	
	public List<Complaint> showComplaints(){
		List<Complaint> result;
		Customer customer;
			
		customer= getCustomerLogged();
		
		result= customerRepository.getComplaints(customer.getUserAccount().getId());
		
		return result;
	}
	
	public Complaint getComplaint(int complaintId){
		Complaint result;
		List<Complaint> complaints;
		List<Integer> ids = new ArrayList<Integer>();
		int i= 0;
		
		complaints= showComplaints();
		
		while(i<complaints.size()){
			ids.add(complaints.get(i).getId());
			i++;
		}
		
		Assert.isTrue(ids.contains(complaintId));
		
		result= complaintService.findOne(complaintId);
		
		return result;
	}

}
