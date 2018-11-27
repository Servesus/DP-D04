
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Report;

@Service
@Transactional
public class ReportService {

	//Managed Repository
	@Autowired
	private ReportRepository	reportRepository;


	//Simple CRUD methods
	public Report create() {
		Report result;

		result = new Report();
		result.setIsFinal(false);

		return result;
	}

	public Collection<Report> findAll() {
		Collection<Report> result;

		result = this.reportRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Report findOne(final Integer reportId) {
		Assert.isTrue(reportId != 0);
		Report result;

		result = this.reportRepository.findOne(reportId);
		Assert.notNull(result);

		return result;
	}

	public Report save(final Report r) {
		Assert.notNull(r);
		Report result;
		Date currentMoment;

		currentMoment = new Date();
		r.setMoment(currentMoment);
		result = this.reportRepository.save(r);

		return result;
	}

	//TODO
	public void delete(final Report r) {
		Assert.notNull(r);
		this.reportRepository.delete(r);
	}

}
