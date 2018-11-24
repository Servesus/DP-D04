
package services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import domain.Curricula;
import domain.PersonalRecord;

@Service
@Transactional
public class CurriculaService {

	//Managed repository
	@Autowired
	private CurriculaRepository		curriculaRepository;

	//Supporting services
	@Autowired
	private PersonalRecordService	personalRecordService;


	//Simple CRUD methods
	public Curricula create() {
		final Curricula curricula = new Curricula();
		final PersonalRecord personalRecord = this.personalRecordService.create();
		curricula.setTicker(CurriculaService.generadorDeTickers());
		curricula.setPersonalRecord(personalRecord);
		return curricula;
	}

	public Collection<Curricula> findAll() {
		return this.curriculaRepository.findAll();
	}

	public Curricula findOne(final int curriculaID) {
		return this.curriculaRepository.findOne(curriculaID);
	}

	public Curricula save(final Curricula curricula) {
		Assert.isNull(curricula);
		return this.curriculaRepository.save(curricula);
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
