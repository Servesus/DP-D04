package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Application;

import repositories.ApplicationRepository;


@Service
@Transactional
public class ApplicationService {
	
	//Managed respositories
	@Autowired
	private ApplicationRepository applicationRepository;
	
	public Application create(){
		Application result;
		
		result= new Application();
		
		return result;
	}
	
	public Collection<Application> findAll() {
		Collection<Application> result;

		result = applicationRepository.findAll();
		Assert.notNull(result);

		return result;
	}
	
	public Application findOne(int applicationId) {
		Application result;

		result = applicationRepository.findOne(applicationId);
		Assert.notNull(result);

		return result;
	}
	
	public Application save(Application application) {
		Assert.notNull(application);

		Application result;
		Date moment= new Date();
		Integer status= 0;
		
		application.setMoment(moment);
		application.setStatus(status);

		result = applicationRepository.save(application);

		return result;
	}

}
