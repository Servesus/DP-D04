
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.LoginService;
import security.UserAccount;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;


	public FixUpTask create() {
		final FixUpTask result = new FixUpTask();
		result.setTicker(CurriculaService.generadorDeTickers());
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
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("CUSTOMER"));
		Assert.notNull(fixUpTask);
		result = this.fixUpTaskRepository.save(fixUpTask);
		return result;
	}

	public void delete(final FixUpTask fixUpTask) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("CUSTOMER"));
		Assert.notNull(fixUpTask);
		assert fixUpTask.getId() != 0;
		Assert.isTrue(this.fixUpTaskRepository.exists(fixUpTask.getId()));
		this.fixUpTaskRepository.delete(fixUpTask);
	}

}
