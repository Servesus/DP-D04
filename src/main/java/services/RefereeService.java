
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	//Managed Repository
	@Autowired
	private RefereeRepository	refereeRepository;


	//Simple CRUD methods
	public Referee create() {
		Referee result;
		UserAccount userAccount;
		Authority aut;
		Collection<Authority> auts;

		auts = new ArrayList<Authority>();
		aut = new Authority();
		userAccount = new UserAccount();
		result = new Referee();

		aut.setAuthority(Authority.REFEREE);
		auts.add(aut);
		userAccount.setAuthorities(auts);

		result.setUserAccount(userAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);

		return result;
	}

	public List<Referee> findAll() {
		return this.refereeRepository.findAll();
	}

	public Referee findOne(final Integer refereeId) {
		Assert.isTrue(refereeId != 0);
		return this.refereeRepository.findOne(refereeId);
	}

	public Referee save(final Referee r) {
		Assert.notNull(r);

		Referee result;
		result = this.refereeRepository.save(r);
		return result;
	}

}
