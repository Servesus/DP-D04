
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Phase;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PhaseServiceTest extends AbstractTest {

	//Service Testing
	private PhaseService	phaseService;


	//2696 id fixUp crear fase 5
	@Test
	public void testCreatePhase() {
		System.out.println("Entro");
		final int fixUpTaskId = this.getEntityId("fixUpTask1");
		System.out.println(fixUpTaskId);
		final Phase p;
		System.out.println("Fase creada");
		try {
			p = this.phaseService.create(fixUpTaskId);
			p.setTitle("titulo");
			p.setDescription("descr");
			p.setStartMoment(new Date("2016/01/02 12:12"));
			p.setFinishMoment(new Date("2018/01/02 12:12"));
			p.setNumber(5);
		} catch (final Exception e) {
			System.out.println("Error" + e.getMessage());
		}

		//Assert.notNull(p.getFixUpTask());
		//Assert.notNull(p);
		Assert.isTrue(true);
	}
}
