
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Category;
import domain.Configuration;
import domain.Finder;
import domain.FixUpTask;
import domain.Warranty;

@Service
@Transactional
public class FinderService {

	//Managed Repository
	@Autowired
	private FinderRepository		finderRepository;
	//Services
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private CategoryService			categoryService;
	@Autowired
	private WarrantyService			warrantyService;


	// HAY QUE REVISARLO
	//Simple CRUD Methods
	public Finder create() {
		Finder result;
		result = new Finder();
		final Collection<FixUpTask> fixUps = new ArrayList<FixUpTask>();
		final Configuration configuration = this.configurationService.create();
		result.setConfiguration(configuration);
		result.setFixUpTask(fixUps);
		result.setRangeFinish(0);
		result.setRangeStart(0);
		final Category category = this.categoryService.findOne(2705);
		result.setCategories(category);
		final Warranty warranty = this.warrantyService.findOne(2715);
		result.setWarranties(warranty);
		result.setSingleKeyWord("");
		final Collection<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();
		result.setFixUpTask(fixUpTasks);
		final Date startDate = new GregorianCalendar(2020, Calendar.NOVEMBER, 30).getTime();
		final Date finishDate = new GregorianCalendar(2021, Calendar.NOVEMBER, 30).getTime();
		result.setDateStartRange(startDate);
		result.setDateFinishRange(finishDate);
		final Date lastUpdate = new Date();
		result.setLastUpdate(lastUpdate);
		return result;
	}
	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		final Date moment = new Date();
		final Date updateFinder = new Date(moment.getTime() + finder.getConfiguration().getMaxTime());
		Assert.isTrue(finder.getLastUpdate().after(updateFinder));
		this.finderRepository.delete(finder);
	}

	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final Integer id) {
		return this.finderRepository.findOne(id);
	}

	public Finder save(Finder finder) {
		Assert.notNull(finder);
		/*
		 * final Double minPrice = finder.getRangeStart() * 1.0;
		 * final Double maxPrice = finder.getRangeFinish() * 1.0;
		 * final Collection<FixUpTask> fixUps = this.finderRepository
		 * .searchFixUpTasks(finder.getSingleKeyWord(), finder.getDateStartRange(), finder.getDateFinishRange(), minPrice, maxPrice, finder.getCategories().getName(), finder.getWarranties().getTitle());
		 * finder.setFixUpTask(fixUps);
		 */
		finder = this.finderRepository.save(finder);
		return finder;
	}

}
