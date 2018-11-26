
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscRecordRepository;
import domain.MiscRecord;

@Service
@Transactional
public class MiscRecordService {

	//Managed repository
	@Autowired
	private MiscRecordRepository	miscRecordRepository;
	//Supporting repositories
	@Autowired
	private ActorService			actorService;
	@Autowired
	private HandyWorkerService		handyWorkerService;


	//Simple CRUD methods
	public MiscRecord create() {
		final MiscRecord miscRecord = new MiscRecord();
		return miscRecord;
	}

	public Collection<MiscRecord> findAll() {
		return this.miscRecordRepository.findAll();
	}

	public MiscRecord findOne(final int miscRecordID) {
		return this.miscRecordRepository.findOne(miscRecordID);
	}

	public MiscRecord save(final MiscRecord miscRecord) {
		Assert.isNull(miscRecord);
		if (miscRecord.getId() == 0)
			this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getMiscRecord().add(miscRecord);
		else {
			Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getMiscRecord().contains(miscRecord));
			this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getMiscRecord().add(miscRecord);
		}
		return this.miscRecordRepository.save(miscRecord);
	}

	public void delete(final MiscRecord miscRecord) {
		Assert.isNull(miscRecord);
		Assert.isTrue(miscRecord.getId() == 0);
		this.miscRecordRepository.delete(miscRecord);
	}

	//Other business methods

}
