
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	@Autowired
	private WarrantyService	warrantyService;


	//	@Test
	//	public void testCreateWarranty() {
	//
	//		final Warranty warranty = this.warrantyService.create();
	//		Assert.notNull(warranty);
	//	}
	@Test
	public void testFindOneWarranty() {

	}
	@Test
	public void testFindAllWarranty() {

	}
	@Test
	public void testSaveWarranty() {

	}
	@Test
	public void testDeleteWarranty() {

	}

}
