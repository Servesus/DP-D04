
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Profile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfileServiceTest extends AbstractTest {

	private ProfileService	profileService;


	@Test
	public void testCreateProfile() {
		final Profile create = this.profileService.create();
		final Profile busqueda = this.profileService.findOne(0);
		Assert.isTrue(create.equals(busqueda));
	}
	@Test
	public void testFindOneProfile() {
		Integer id;
		id = 2631;
		Profile a;
		a = this.profileService.findOne(id);
		Assert.isTrue(a.getName().equals("valenciano"));
	}

	@Test
	public void testFindAllProfile() {
		final Collection<Profile> res = this.profileService.findAll();
		Assert.isTrue(res.size() == 5);
	}
}
