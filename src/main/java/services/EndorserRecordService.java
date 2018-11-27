
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

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
		Assert.isNull(endorserRecord);
		if (endorserRecord.getId() == 0)
			this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getEndorserRecord().add(endorserRecord);
		else {
			Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getEndorserRecord().contains(endorserRecord));
			this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getEndorserRecord().add(endorserRecord);
		}
		return this.endorserRecordRepository.save(endorserRecord);
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.isNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() == 0);
		this.endorserRecordRepository.delete(endorserRecord);
	}

	//Other business methods

}
