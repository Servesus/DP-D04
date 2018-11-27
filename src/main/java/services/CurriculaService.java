
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import domain.Curricula;
import domain.EducationalRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculaService {

	//Managed repository
	@Autowired
	private CurriculaRepository		curriculaRepository;

	//Supporting services
	@Autowired
	private PersonalRecordService	personalRecordService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private HandyWorkerService		handyWorkerService;


	//Simple CRUD methods
	public Curricula create() {
		final Curricula curricula = new Curricula();
		final PersonalRecord personalRecord = this.personalRecordService.create();
		curricula.setTicker(CurriculaService.generadorDeTickers());
		curricula.setPersonalRecord(personalRecord);
		curricula.setEducationalRecord(new ArrayList<EducationalRecord>());
		curricula.setEndorserRecord(new ArrayList<EndorserRecord>());
		curricula.setProfessionalRecord(new ArrayList<ProfessionalRecord>());
		curricula.setMiscRecord(new ArrayList<MiscRecord>());
		return curricula;
	}
	public Collection<Curricula> findAll() {
		return this.curriculaRepository.findAll();
	}

	public Curricula findOne(final int curriculaID) {
		return this.curriculaRepository.findOne(curriculaID);
	}

	public Curricula save(final Curricula curricula) {
		final Curricula result = this.curriculaRepository.save(curricula);
		Assert.isNull(result);
		final HandyWorker hw = this.handyWorkerService.findOne(this.actorService.getActorLogged().getId());
		if (result.getId() == 0) {
			hw.setCurricula(result);
			this.handyWorkerService.save(hw);
		} else {
			Assert.isTrue(hw.getCurricula().getId() == result.getId());
			hw.setCurricula(result);
			this.handyWorkerService.save(hw);
		}
		return result;
	}

	public void delete(final Curricula curricula) {
		Assert.isNull(curricula);
		Assert.isTrue(curricula.getId() == 0);
		this.curriculaRepository.delete(curricula);
	}

	//Other business methods

	public static String generadorDeTickers() {
		String dateRes = "";
		String numericRes = "";
		final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdfghijklmnopqrstuvwxyz";
		dateRes = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());

		for (int i = 0; i < 6; i++) {
			final Random random = new Random();
			numericRes = numericRes + alphanumeric.charAt(random.nextInt(alphanumeric.length() - 1));
		}

		return dateRes + "-" + numericRes;
	}

}
