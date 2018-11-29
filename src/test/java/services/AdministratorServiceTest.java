
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service testing
	@Autowired
	private AdministratorService	administratorService;

	//Other services
	@Autowired
	private ActorService			actorService;


	@Test
	public void create() {
		super.authenticate("admin1");

		Administrator a;
		a = this.administratorService.create();
		a.setName("admin2");
		a.setSurname("admin2");
		a.setEmail("admin2@gmail.com");
		Assert.notNull(a);

		super.authenticate(null);
	}

	@Test
	public void save() {

		super.authenticate("admin1");

		Administrator a;
		Administrator saved;
		UserAccount userAccount;
		Collection<Administrator> admins;

		a = this.administratorService.create();
		userAccount = a.getUserAccount();
		userAccount.setUsername("admin2");
		userAccount.setPassword("02061997");
		a.setUserAccount(userAccount);
		a.setName("admin2");
		a.setSurname("admin2");
		a.setEmail("admin2@gmail.com");

		saved = this.administratorService.save(a);

		admins = this.administratorService.findAll();

		Assert.isTrue(admins.contains(saved));

		super.authenticate(null);
	}
	@Test
	public void findOne() {
		Integer id;
		id = this.getEntityId("admin1");
		Administrator a;
		a = this.administratorService.findOne(id);
		Assert.isTrue(a.getName().equals("admin1"));
		Assert.notNull(a);
	}

	@Test
	public void findAll() {
		Collection<Administrator> admins;
		admins = this.administratorService.findAll();
		Assert.isTrue(admins.size() == 1);
		Assert.notNull(admins);
	}

	@Test
	public void delete() {
		super.authenticate("admin1");

		Administrator a;
		Integer id;
		id = this.getEntityId("admin1");
		a = this.administratorService.findOne(id);
		this.administratorService.delete(a);
		Assert.isNull(this.administratorService.findOne(id));

		super.authenticate(null);
	}

	@Test
	public void banActor() {
		super.authenticate("admin1");
		Integer id;
		//		Actor a;
		Actor saved;
		id = this.getEntityId("customer1");
		System.out.println(id);
		saved = this.administratorService.banActor(id);
		//		a = this.actorService.findOne(id);
		System.out.println(saved);
		Assert.isTrue(saved.getIsBanned());
		super.authenticate(null);

	}
}
