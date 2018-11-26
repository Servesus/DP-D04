
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	//Managed repository
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;
	//Supporting repositories
	@Autowired
	private ActorService					actorService;
	@Autowired
	private HandyWorkerService				handyWorkerService;


	//Simple CRUD methods
	public ProfessionalRecord create() {
		final ProfessionalRecord professionalRecord = new ProfessionalRecord();
		return professionalRecord;
	}

	public Collection<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}

	public ProfessionalRecord findOne(final int professionalRecordID) {
		return this.professionalRecordRepository.findOne(professionalRecordID);
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.isNull(professionalRecord);
		if (professionalRecord.getId() == 0)
			this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getProfessionalRecord().add(professionalRecord);
		else {
			Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getProfessionalRecord().contains(professionalRecord));
			this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula().getProfessionalRecord().add(professionalRecord);
		}
		return this.professionalRecordRepository.save(professionalRecord);
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.isNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() == 0);
		this.professionalRecordRepository.delete(professionalRecord);
	}

	//Other business methods

}
