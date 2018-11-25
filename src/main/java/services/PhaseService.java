
package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	//Managed repository
	@Autowired
	private PhaseRepository	phaseRepository;


	//Simple CRUD methods
	public Phase create() {
		Phase result;
		result = new Phase();
		return result;
	}

	public Phase save(final Phase phase) {
		Assert.notNull(phase);
		Phase result;
		Date currentMoment;
		currentMoment = new Date();

		Assert.isTrue(phase.getStartMoment().after(currentMoment));
		Assert.isTrue(phase.getStartMoment().before(phase.getFinishMoment()));

		result = this.phaseRepository.save(phase);

		return result;
	}
	public List<Phase> findAll() {
		return this.phaseRepository.findAll();
	}

	public Phase findOne(final Integer phaseId) {
		return this.phaseRepository.findOne(phaseId);
	}

	public void delete(final Phase phase) {
		Assert.notNull(phase);
		Assert.isTrue(phase.getId() != 0);

		this.phaseRepository.delete(phase);
	}
}
