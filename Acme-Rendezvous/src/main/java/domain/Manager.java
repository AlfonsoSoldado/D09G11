package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	// Attributes -----------------------------------------------------

	private String VAT;

	public String getVAT() {
		return VAT;
	}

	public void setVAT(String vAT) {
		VAT = vAT;
	}

	// Relationships --------------------------------------------------
	
	private Collection<Services> services;

	@Valid
	@OneToMany
	public Collection<Services> getServices() {
		return services;
	}

	public void setServices(Collection<Services> services) {
		this.services = services;
	}
	
}
