
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import domain.Category;
import domain.FixUpTask;
import domain.Warranty;

@Service
@Transactional
public class FixUpTaskService {

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;
	//Supporting services
	@Autowired
	private WarrantyService		warrantyService;
	@Autowired
	private CategoryService		categoryService;


	public FixUpTask create() {
		final FixUpTask result = new FixUpTask();
		final Warranty warranty = this.warrantyService.create();
		final Category category = this.categoryService.create();
		result.setTicker(CurriculaService.generadorDeTickers());
		result.setWarranty(warranty);
		result.setCategory(category);
		return result;
	}

	public Collection<FixUpTask> findAll() {
		Collection<FixUpTask> result;
		Assert.notNull(this.fixUpTaskRepository);
		result = this.fixUpTaskRepository.findAll();
		return result;
	}

	public FixUpTask findOne(final int fixUpTaskId) {
		FixUpTask result;
		Assert.notNull(this.fixUpTaskRepository);
		result = this.fixUpTaskRepository.findOne(fixUpTaskId);
		return result;

	}

	public FixUpTask save(final FixUpTask fixUpTask) {

		FixUpTask result;
		Assert.notNull(fixUpTask);
		result = this.fixUpTaskRepository.save(fixUpTask);
		return result;
	}

	public void delete(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		assert fixUpTask.getId() != 0;
		Assert.isTrue(this.fixUpTaskRepository.exists(fixUpTask.getId()));
		this.fixUpTaskRepository.delete(fixUpTask);
	}

}
