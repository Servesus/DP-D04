
package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Configuration;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	//Managed Repository
	@Autowired
	private FinderRepository		finderRepository;
	//Services
	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD Methods
	public Finder create() {
		Finder result;
		result = new Finder();
		final Configuration configuration = this.configurationService.create();
		result.setConfiguration(configuration);
		return result;
	}
	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		final Date moment = new Date();
		final Date updateFinder = new Date(moment.getTime() + finder.getConfiguration().getMaxTime());
		Assert.isTrue(finder.getConfiguration().getLastUpdate().after(updateFinder));
		this.finderRepository.delete(finder);
	}

	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final Integer id) {
		return this.finderRepository.findOne(id);
	}

	/*
	 * public Finder save(Finder finder){
	 * Assert.notNull(finder);
	 * Configuration config;
	 * Configuration configuration = finder.getConfiguration();
	 * config = this.configurationService.save(configuration);
	 * finder.setConfiguration(config);
	 * Double minPrice = finder.getRangeStart()*1.0;
	 * Double maxPrice = finder.getRangeFinish()*1.0;
	 * //TODO CAMBIAR MULTIPLICIDADES DE CATEGORY Y WARRANTY EN FINDER
	 * //List<FixUpTask> fixUps = this.finderRepository.searchFixUpTasks(finder.getSingleKeyWord(), finder.getDateStartRange(), finder.getDateFinishRange(), minPrice, maxPrice, finder.getCategories(), warrantyName);
	 * }
	 */

}
