
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	private String				name;
	private Boolean				isSystem;
	private Collection<Box>		parentBoxes;
	private Collection<Box>		childBoxes;
	private Collection<Message>	messages;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public Boolean getIsSystem() {
		return this.isSystem;
	}
	public void setIsSystem(final Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@ManyToMany
	public Collection<Box> getParentBoxes() {
		return this.parentBoxes;
	}
	public void setParentBoxes(final Collection<Box> parentBoxes) {
		this.parentBoxes = parentBoxes;
	}

	@ManyToMany(mappedBy = "parentBoxes")
	public Collection<Box> getChildBoxes() {
		return this.childBoxes;
	}
	public void setChildBoxes(final Collection<Box> childBoxes) {
		this.childBoxes = childBoxes;
	}
	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}
	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}
}
