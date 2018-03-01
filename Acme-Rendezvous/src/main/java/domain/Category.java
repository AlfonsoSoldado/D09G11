package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity  {

	// Attributes ------------------------------------------------------

	private String name;
	private String description;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Relationships --------------------------------------------------
	
	private Collection<Services> services;

	@Valid
	@OneToMany(mappedBy = "category")
	public Collection<Services> getServices() {
		return services;
	}

	public void setServices(Collection<Services> services) {
		this.services = services;
	}
	
}
