
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	//Attributes
	private int		maxResults	= 10;
	private int		maxTime		= 1;
	private Date	lastUpdate;


	@Range(min = 10, max = 100)
	public int getMaxResults() {
		return this.maxResults;
	}

	public void setMaxResults(final int maxResults) {
		this.maxResults = maxResults;
	}
	@Range(min = 1, max = 24)
	public int getMaxTime() {
		return this.maxTime;
	}

	public void setMaxTime(final int maxTime) {
		this.maxTime = maxTime;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	//Relationships
	private Finder	finder;


	@OneToOne(mappedBy = "configuration")
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
