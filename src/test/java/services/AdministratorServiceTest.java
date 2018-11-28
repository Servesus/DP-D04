
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
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


	@Test
	//TODO : Probar inicializando profiles en el create de Admin
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
		Administrator a;
		Administrator saved;
		final Collection<Administrator> admins;

		super.authenticate("admin1");

		a = this.administratorService.create();
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
	}

	@Test
	public void findAll() {
		Collection<Administrator> admins;
		admins = this.administratorService.findAll();
		Assert.isTrue(admins.size() == 1);
	}

}
