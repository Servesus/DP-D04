
package services;

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

	//Simple CRUD methods
	public EndorserRecord create() {
		final EndorserRecord endorserRecord = new EndorserRecord();
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
		return this.endorserRecordRepository.save(endorserRecord);
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.isNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() == 0);
		this.endorserRecordRepository.delete(endorserRecord);
	}

	//Other business methods

}