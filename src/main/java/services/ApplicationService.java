
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	//Managed respositories
	@Autowired
	private ApplicationRepository	applicationRepository;


	public Application create() {
		Application result;

		result = new Application();

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

		Application result;
		final Date moment = new Date();
		final Integer status = 0;

		application.setMoment(moment);
		application.setStatus(status);

		result = this.applicationRepository.save(application);

		return result;
	}

}
