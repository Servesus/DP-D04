
package services;

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
		this.finderRepository.delete(finder);
	}

	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final Integer id) {
		return this.finderRepository.findOne(id);
	}

	//TODO public Finder save(Finder finder){

}
