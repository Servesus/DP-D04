
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private HandyWorkerService	handyWorkerService;


	@Test
	public void testCreate() {
		final HandyWorker h = this.handyWorkerService.create();
		Assert.notNull(h);
	}

	@Test
	public void testSave() {
		final HandyWorker h = this.handyWorkerService.create();
		h.setAddress("hola");
	}

}
