package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	// Attributes -----------------------------------------------------

	private String VAT;

	@NotBlank
	@Pattern(regexp = "^[\\w-]+$")
	public String getVAT() {
		return VAT;
	}

	public void setVAT(String VAT) {
		this.VAT = VAT;
	}

	// Relationships --------------------------------------------------
	
}
