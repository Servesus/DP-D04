
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.UserAccount;
import domain.Box;
import domain.Finder;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	//Managed repository
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;
	//Servicios
	@Autowired
	private FinderService			finderService;
	@Autowired
	private BoxService				boxService;


	//Simple CRUD Methods

	public HandyWorker create() {
		HandyWorker result;
		UserAccount user;
		Authority aut;
		Collection<Authority> auts;
		Finder finder;

		auts = new ArrayList<Authority>();
		aut = new Authority();
		user = new UserAccount();
		result = new HandyWorker();
		finder = this.finderService.create();

		aut.setAuthority(Authority.HANDYWORKER);
		auts.add(aut);
		user.setAuthorities(auts);

		result.setUserAccount(user);
		result.setIsBanned(false);
		result.setIsSuspicious(false);
		result.setFinder(finder);
		return result;
	}

	public Collection<HandyWorker> findAll() {
		Collection<HandyWorker> result;

		result = this.handyWorkerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public HandyWorker findOne(final int handyWorkerId) {
		HandyWorker result;

		result = this.handyWorkerRepository.findOne(handyWorkerId);
		Assert.notNull(result);

		return result;
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		if (handyWorker.getId() == 0) {
			Collection<Box> boxSystem;
			boxSystem = this.boxService.createSystemBoxes();
			handyWorker.setBoxes(boxSystem);
			Finder finder;
			finder = handyWorker.getFinder();
			finder = this.finderService.save(finder);
			handyWorker.setFinder(finder);
		}
		HandyWorker result;
		result = this.handyWorkerRepository.save(handyWorker);
		return result;
	}

	public void delete(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);

		this.handyWorkerRepository.delete(handyWorker);
	}

	//TODO METODO QUE SACA LAS APPLICATIONS DEL HANDY WORKER LOGGEADO
}
