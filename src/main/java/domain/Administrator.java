
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	//Relationships 
	private Collection<Category>		categories;
	private Collection<Configuration>	configurations;


	@OneToMany
	public Collection<Category> getCategories() {
		return this.categories;
	}
	@OneToMany
	public Collection<Configuration> getConfigurations() {
		return this.configurations;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}

	public void setConfigurations(final Collection<Configuration> configurations) {
		this.configurations = configurations;
	}

}
