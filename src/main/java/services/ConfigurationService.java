
package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed repository
	@Autowired
	private ConfigurationRepository	configurationRepository;


	//Simple CRUD Methods

	public Configuration create() {
		Configuration result;
		result = new Configuration();
		result.setMaxResults(10);
		result.setMaxTime(1);
		return result;
	}

	public void delete(final Configuration arg0) {

		this.configurationRepository.delete(arg0);
	}

	public List<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findOne(final Integer arg0) {
		return this.configurationRepository.findOne(arg0);
	}
	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		Configuration result;
		Date currentMoment;
		currentMoment = new Date();
		configuration.setLastUpdate(currentMoment);
		result = this.configurationRepository.save(configuration);
		return result;
	}

}
