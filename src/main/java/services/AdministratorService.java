
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Category;
import domain.Configuration;
import domain.Customer;
import domain.HandyWorker;

@Service
@Transactional
public class AdministratorService {

	//Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting services
	@Autowired
	private BoxService				boxService;
	@Autowired
	private ActorService			actorService;


	//Simple CRUD methods
	public Administrator create() {
		UserAccount userAccount;
		UserAccount nowUserAccount;
		Authority aut;
		Administrator result;
		Collection<Authority> auts;
		Collection<Category> categories;
		Collection<Configuration> configurations;

		categories = new ArrayList<Category>();
		configurations = new ArrayList<Configuration>();

		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		auts = new ArrayList<Authority>();
		aut = new Authority();
		nowUserAccount = new UserAccount();
		result = new Administrator();

		aut.setAuthority(Authority.ADMIN);
		auts.add(aut);
		userAccount.setAuthorities(auts);

		result.setCategories(categories);
		result.setConfigurations(configurations);
		result.setUserAccount(nowUserAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);

		return result;
	}
	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator findOne(final Integer administratorId) {
		Assert.isTrue(administratorId != 0);
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	public Administrator save(final Administrator a) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));
		Assert.notNull(a);

		final Administrator result;

		if (a.getId() == 0) {
			Collection<Box> boxSystem;
			boxSystem = this.boxService.createSystemBoxes();
			a.setBoxes(boxSystem);
		}

		result = this.administratorRepository.save(a);
		return result;
	}
	public void delete(final Administrator a) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));
		Assert.notNull(a);
		Assert.isTrue(a.getId() != 0);
		this.administratorRepository.delete(a);
	}

	//Other business methods 

	/* Q1 */
	public List<Double> getStatsFixUpTasksPerUser() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<Double> result;
		result = new ArrayList<Double>();
		result.add(this.administratorRepository.getAvgOfFixUpTasksPerUser());
		result.add(this.administratorRepository.getMinOfFixUpTasksPerUser());
		result.add(this.administratorRepository.getMaxOfFixUpTasksPerUser());
		result.add(this.administratorRepository.getStddevOfFixUpTasksPerUser());
		return result;
	}

	/* Q2 */
	public List<Double> getStatsApplicationsPerFixUpTask() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<Double> result;
		result = new ArrayList<Double>();
		result.add(this.administratorRepository.getAvgApplicationsPerFixUpTask());
		result.add(this.administratorRepository.getMinApplicationsPerFixUpTask());
		result.add(this.administratorRepository.getMaxApplicationsPerFixUpTask());
		result.add(this.administratorRepository.getStddevApplicationsPerFixUpTask());

		return result;
	}

	/* Q3 */
	public List<Double> getStatsMaxPriceOfFixUpTask() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<Double> result;
		result = new ArrayList<Double>();
		result.add(this.administratorRepository.getAvgMaxPricePerFixUpTask());
		result.add(this.administratorRepository.getMinMaxPricePerFixUpTask());
		result.add(this.administratorRepository.getMaxMaxPricePerFixUpTask());
		result.add(this.administratorRepository.getStddevMaxPricePerFixUpTask());

		return result;
	}

	/* Q4 */
	public List<Double> getStatsPriceOfferedInApplications() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<Double> result;
		result = new ArrayList<Double>();
		result.add(this.administratorRepository.getAvgPriceOfferedOfApplication());
		result.add(this.administratorRepository.getMinPriceOfferedOfApplication());
		result.add(this.administratorRepository.getMaxPriceOfferedOfApplication());
		result.add(this.administratorRepository.getStddevPriceOfferedOfApplciation());

		return result;
	}

	/* Q5 */
	public Double getRatioPendingApplications() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		Double result;
		result = this.administratorRepository.getRatioOfPendingApplications();

		return result;
	}

	/* Q6 */
	public Double getRatioAcceptedApplications() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		Double result;
		result = this.administratorRepository.getRatioOfAcceptedApplications();
		return result;
	}

	/* Q7 */
	public Double getRatioRejectedApplications() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		Double result;
		result = this.administratorRepository.getRatioOfRejectedApplications();
		return result;
	}

	/* Q8 */
	public Double getRatioOfPendingApplicationsCanNotChangeStatus() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		Double result;
		result = this.administratorRepository.getRatioOfPendingApplicationsCanNotChangeStatus();
		return result;
	}

	/* Q9 */
	public List<Customer> customerMoreAcceptedThanAvg() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<Customer> result;
		result = this.administratorRepository.getCustomerMoreAcceptedThanAvg();
		return result;
	}

	/* Q10 */
	public List<HandyWorker> getHwMoreAcceptedThanAvg() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<HandyWorker> result;
		result = this.administratorRepository.getHwMoreAcceptedThanAvg();
		return result;
	}

	/* Q11 */
	public List<Double> getStatsComplaintsPerFixUpTask() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<Double> result;
		result = new ArrayList<Double>();
		result.add(this.administratorRepository.getAvgComplaintsPerFixUpTask());
		result.add(this.administratorRepository.getMinComplaintsPerFixUpTask());
		result.add(this.administratorRepository.getMaxComplaintsPerFixUpTask());
		result.add(this.administratorRepository.getStddevComplaintsPerFixUpTask());

		return result;
	}

	/* Q12 */
	public List<Double> getStatsNotesPerRefereeReport() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<Double> result;
		result = new ArrayList<Double>();
		result.add(this.administratorRepository.getAvgNotesPerRefereeReport());
		result.add(this.administratorRepository.getMinNotesPerRefereeReport());
		result.add(this.administratorRepository.getMaxNotesPerRefereeReport());
		result.add(this.administratorRepository.getStddevNotesPerRefereeReport());

		return result;
	}

	/* Q13 */
	public Double getRatioFixUpTaskWithComplaint() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		Double result;
		result = this.administratorRepository.getRatioFixUpTaskWithComplaint();
		return result;
	}

	/* Q14 */
	public List<Customer> getTop3CustomersOfComplaints() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<Customer> result;
		result = this.administratorRepository.getTop3CustomersOfComplaints().subList(0, 2);

		return result;
	}

	/* Q15 */
	public List<HandyWorker> getTop3HandyWorkerOfComplaints() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));

		List<HandyWorker> result;
		result = this.administratorRepository.getTop3HandyWorkerOfComplaints().subList(0, 2);

		return result;
	}

	public void banActor(final Integer id) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));
		Actor a;
		a = this.actorService.findOne(id);
		a.setIsBanned(true);
		this.actorService.save(a);
	}

	public void unbanActor(final Integer id) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));
		Actor a;
		a = this.actorService.findOne(id);
		a.setIsBanned(false);
		this.actorService.save(a);
	}

	public List<Actor> getSuspicious() {
		Collection<Actor> actors;
		List<Actor> result;
		result = new ArrayList<Actor>();
		actors = this.actorService.findAll();
		for (final Actor a : actors)
			if (a.getIsSuspicious() == true)
				result.add(a);
		return result;
	}
}
