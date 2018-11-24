package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;

import repositories.ActorRepository;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class ActorService {
	
	//Managed Repositories
	@Autowired
	private ActorRepository actorRepository;
	
	//Supporting services
	@Autowired
	private UserAccountService userAccountService;

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}
	
	public void delete(Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(actorRepository.exists(actor.getId()));

		actorRepository.delete(actor);
	}
	
	public UserAccount findUserAccount(Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = userAccountService.findByActor(actor);

		return result;
	}
}
