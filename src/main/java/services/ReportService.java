
package services;

import java.util.Date;
import java.util.List;

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

	public List<Report> findAll() {
		return this.reportRepository.findAll();
	}

	public Report findOne(final Integer reportId) {
		Assert.isTrue(reportId != 0);
		return this.reportRepository.findOne(reportId);
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
