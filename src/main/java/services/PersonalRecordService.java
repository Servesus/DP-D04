
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	//Managed repository
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;


	//Supporting repositories

	//Simple CRUD methods
	public PersonalRecord create() {
		final PersonalRecord personalRecord = new PersonalRecord();
		return personalRecord;
	}

	public Collection<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(final int personalRecordID) {
		return this.personalRecordRepository.findOne(personalRecordID);
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.isNull(personalRecord);
		return this.personalRecordRepository.save(personalRecord);
	}

	public void delete(final PersonalRecord personalRecord) {
		Assert.isNull(personalRecord);
		Assert.isTrue(personalRecord.getId() == 0);
		this.personalRecordRepository.delete(personalRecord);
	}

	//Other business methods

}
