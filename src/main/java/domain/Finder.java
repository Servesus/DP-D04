
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String	singleKeyWord;
	private Integer	rangeStart;
	private Integer	rangeFinish;
	private Date	dateStartRange;
	private Date	dateFinishRange;
	private Date	lastUpdate;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getSingleKeyWord() {
		return this.singleKeyWord;
	}

	public void setSingleKeyWord(final String singleKeyWord) {
		this.singleKeyWord = singleKeyWord;
	}

	public Integer getRangeStart() {
		return this.rangeStart;
	}

	public void setRangeStart(final Integer rangeStart) {
		this.rangeStart = rangeStart;
	}

	public Integer getRangeFinish() {
		return this.rangeFinish;
	}

	public void setRangeFinish(final Integer rangeFinish) {
		this.rangeFinish = rangeFinish;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	public Date getDateStartRange() {
		return this.dateStartRange;
	}

	public void setDateStartRange(final Date dateStartRange) {
		this.dateStartRange = dateStartRange;
	}
	@NotNull
	@Temporal(TemporalType.DATE)
	public Date getDateFinishRange() {
		return this.dateFinishRange;
	}

	public void setDateFinishRange(final Date dateFinishRange) {
		this.dateFinishRange = dateFinishRange;
	}


	//Relationships
	private Configuration			configuration;
	private Collection<FixUpTask>	fixUpTask;
	private Warranty				warranties;
	private Category				categories;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public Configuration getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(final Configuration configuration) {
		this.configuration = configuration;
	}

	@ManyToMany
	public Collection<FixUpTask> getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final Collection<FixUpTask> fixUpTask) {
		this.fixUpTask = fixUpTask;
	}

	@ManyToOne
	public Warranty getWarranties() {
		return this.warranties;
	}

	public void setWarranties(final Warranty warranties) {
		this.warranties = warranties;
	}

	@ManyToOne
	public Category getCategories() {
		return this.categories;
	}

	public void setCategories(final Category categories) {
		this.categories = categories;
	}

}
