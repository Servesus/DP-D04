
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscRecordRepository;
import security.LoginService;
import domain.HandyWorker;
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
	@Autowired
	private CurriculaService		curriculaService;


	//Simple CRUD methods
	public MiscRecord create() {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains("HANDYWORKER"));
		Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula() != null);
		final MiscRecord miscRecord = new MiscRecord();
		miscRecord.setComments(new ArrayList<String>());
		return miscRecord;
	}

	public Collection<MiscRecord> findAll() {
		return this.miscRecordRepository.findAll();
	}

	public MiscRecord findOne(final int miscRecordID) {
		return this.miscRecordRepository.findOne(miscRecordID);
	}

	public MiscRecord save(final MiscRecord miscRecord) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains("HANDYWORKER"));
		Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula() != null);
		final MiscRecord result = this.miscRecordRepository.save(miscRecord);
		Assert.isNull(result);
		final HandyWorker hw = this.handyWorkerService.findOne(this.actorService.getActorLogged().getId());
		if (result.getId() == 0) {
			final List<MiscRecord> mR = (List<MiscRecord>) hw.getCurricula().getMiscRecord();
			mR.add(result);
			hw.getCurricula().setMiscRecord(mR);
			this.curriculaService.save(hw.getCurricula());
		} else {
			Assert.isTrue(hw.getCurricula().getEndorserRecord().contains(result));
			final List<MiscRecord> mR = (List<MiscRecord>) hw.getCurricula().getMiscRecord();
			mR.add(result);
			hw.getCurricula().setMiscRecord(mR);
			this.curriculaService.save(hw.getCurricula());
		}
		return result;
	}

	public void delete(final MiscRecord miscRecord) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains("HANDYWORKER"));
		Assert.isTrue(this.handyWorkerService.findOne(this.actorService.getActorLogged().getId()).getCurricula() != null);
		Assert.isNull(miscRecord);
		Assert.isTrue(miscRecord.getId() != 0);
		this.miscRecordRepository.delete(miscRecord);
	}

	//Other business methods

}
