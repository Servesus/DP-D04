
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.LoginService;
import domain.HandyWorker;
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
	@Autowired
	private CurriculaService				curriculaService;


	//Simple CRUD methods
	public ProfessionalRecord create() {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains("HANDYWORKER"));
		Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula() != null);
		final ProfessionalRecord professionalRecord = new ProfessionalRecord();
		professionalRecord.setComment(new ArrayList<String>());
		return professionalRecord;
	}

	public Collection<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}

	public ProfessionalRecord findOne(final int professionalRecordID) {
		return this.professionalRecordRepository.findOne(professionalRecordID);
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains("HANDYWORKER"));
		Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula() != null);
		final ProfessionalRecord result = this.professionalRecordRepository.save(professionalRecord);
		Assert.isNull(result);
		final HandyWorker hw = this.handyWorkerService.findOne(this.actorService.getActorLogged().getId());
		if (result.getId() == 0) {
			final List<ProfessionalRecord> pR = (List<ProfessionalRecord>) hw.getCurricula().getProfessionalRecord();
			pR.add(result);
			hw.getCurricula().setProfessionalRecord(pR);
			this.curriculaService.save(hw.getCurricula());
		} else {
			Assert.isTrue(hw.getCurricula().getEndorserRecord().contains(result));
			final List<ProfessionalRecord> pR = (List<ProfessionalRecord>) hw.getCurricula().getProfessionalRecord();
			pR.add(result);
			hw.getCurricula().setProfessionalRecord(pR);
			this.curriculaService.save(hw.getCurricula());
		}
		return result;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains("HANDYWORKER"));
		Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula() != null);
		Assert.isNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);
		this.professionalRecordRepository.delete(professionalRecord);
	}

	//Other business methods

}
