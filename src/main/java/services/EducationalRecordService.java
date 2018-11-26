
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationalRecordRepository;
import domain.EducationalRecord;

@Service
@Transactional
public class EducationalRecordService {

	//Managed repository
	@Autowired
	private EducationalRecordRepository	educationalRecordRepository;
	//Supporting repositories
	@Autowired
	private ActorService				actorService;
	@Autowired
	private HandyWorkerService			handyWorkerService;


	//Simple CRUD methods
	public EducationalRecord create() {
		final EducationalRecord educationalRecord = new EducationalRecord();
		return educationalRecord;
	}

	public Collection<EducationalRecord> findAll() {
		return this.educationalRecordRepository.findAll();
	}

	public EducationalRecord findOne(final int educationalRecordID) {
		return this.educationalRecordRepository.findOne(educationalRecordID);
	}

	public EducationalRecord save(final EducationalRecord educationalRecord) {
		Assert.isNull(educationalRecord);
		if (educationalRecord.getId() == 0)
			this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getEducationalRecord().add(educationalRecord);
		else {
			Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getEducationalRecord().contains(educationalRecord));
			this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getEducationalRecord().add(educationalRecord);
		}
		return this.educationalRecordRepository.save(educationalRecord);
	}

	public void delete(final EducationalRecord educationalRecord) {
		Assert.isNull(educationalRecord);
		Assert.isTrue(educationalRecord.getId() == 0);
		this.educationalRecordRepository.delete(educationalRecord);
	}

	//Other business methods

}
