
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	//Managed repository
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;
	//Supporting repositories
	@Autowired
	private ActorService				actorService;
	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private CurriculaService			curriculaService;


	//Simple CRUD methods
	public EndorserRecord create() {
		final EndorserRecord endorserRecord = new EndorserRecord();
		endorserRecord.setComments(new ArrayList<String>());
		return endorserRecord;
	}

	public Collection<EndorserRecord> findAll() {
		return this.endorserRecordRepository.findAll();
	}

	public EndorserRecord findOne(final int endorserRecordID) {
		return this.endorserRecordRepository.findOne(endorserRecordID);
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		final EndorserRecord result = this.endorserRecordRepository.save(endorserRecord);
		Assert.isNull(result);
		final HandyWorker hw = this.handyWorkerService.findOne(this.actorService.getActorLogged().getId());
		if (result.getId() == 0) {
			final List<EndorserRecord> eR = (List<EndorserRecord>) hw.getCurricula().getEndorserRecord();
			eR.add(result);
			hw.getCurricula().setEndorserRecord(eR);
			this.curriculaService.save(hw.getCurricula());
		} else {
			Assert.isTrue(hw.getCurricula().getEndorserRecord().contains(result));
			final List<EndorserRecord> eR = (List<EndorserRecord>) hw.getCurricula().getEndorserRecord();
			eR.add(result);
			hw.getCurricula().setEndorserRecord(eR);
			this.curriculaService.save(hw.getCurricula());
		}
		return result;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.isNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() == 0);
		this.endorserRecordRepository.delete(endorserRecord);
	}

	//Other business methods

}
