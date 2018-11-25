
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	//Managed repository
	@Autowired
	private AdministratorRepository	administratorRepository;


	//Simple CRUD methods
	//TODO
	public Administrator create() {
		UserAccount userAccount;
		UserAccount nowUserAccount;
		Authority aut;
		Administrator result;

		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));
		result = new Administrator();
		nowUserAccount = new UserAccount();
		result.setUserAccount(nowUserAccount);
		aut = new Authority();
		aut.setAuthority(Authority.ADMIN);
		result.setIsBanned(false);
		result.setIsSuspicious(false);

		return result;
	}
	public List<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final Integer administratorId) {
		Assert.isTrue(administratorId != 0);
		return this.administratorRepository.findOne(administratorId);
	}

	public Administrator save(final Administrator a) {
		Assert.notNull(a);

		Administrator result;
		result = this.administratorRepository.save(a);

		return result;
	}

	public void delete(final Administrator a) {
		Assert.notNull(a);
		this.administratorRepository.delete(a);
	}
}
