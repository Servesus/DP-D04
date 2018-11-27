
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import security.LoginService;
import security.UserAccount;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	@Autowired
	private WarrantyRepository	warrantyRepository;


	public Warranty create() {
		Warranty result;
		final Boolean res = false;
		result = new Warranty();
		result.setIsFinal(res);
		return result;
	}

	public Collection<Warranty> findAll() {
		Collection<Warranty> result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));
		Assert.notNull(this.warrantyRepository);
		result = this.warrantyRepository.findAll();
		return result;
	}

	public Warranty findOne(final int warrantyId) {
		Warranty result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));
		Assert.notNull(this.warrantyRepository);
		result = this.warrantyRepository.findOne(warrantyId);
		return result;

	}

	public Warranty save(final Warranty warranty) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));
		Assert.notNull(warranty);
		if (warranty.getId() == 0)
			Assert.isTrue(warranty.getIsFinal() == true);
		Warranty result;
		result = this.warrantyRepository.save(warranty);
		return result;
	}

	public void delete(final Warranty warranty) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains("ADMIN"));
		Assert.notNull(warranty);
		assert warranty.getId() != 0;
		Assert.isTrue(warranty.getIsFinal() == true);
		Assert.isTrue(this.warrantyRepository.exists(warranty.getId()));
		this.warrantyRepository.delete(warranty);
	}
}
