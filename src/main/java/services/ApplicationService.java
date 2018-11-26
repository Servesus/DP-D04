
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	//Managed respositories
	@Autowired
	private ApplicationRepository	applicationRepository;
	
	public Application create() {
		Application result;
		
		final Date moment = new Date();
		final Integer status = 0;

		result = new Application();
		
		result.setMoment(moment);
		result.setStatus(status);

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);
		Assert.notNull(result);

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		UserAccount userAccount;
		
		userAccount= LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("CUSTOMER") || 
				userAccount.getAuthorities().contains("HANDYWORKER"));

		Application result;
		
		result = this.applicationRepository.save(application);

		return result;
	}

}
