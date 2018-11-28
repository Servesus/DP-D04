
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Phase;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PhaseServiceTest extends AbstractTest {

	//Service Testing
	private PhaseService	phaseService;


	//2696 id fixUp crear fase 5
	@Test
	public void testSavePhase() {
		final Phase p = this.phaseService.create(2696);
	}
}
