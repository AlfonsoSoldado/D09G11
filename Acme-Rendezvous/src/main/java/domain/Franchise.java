package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Franchise extends DomainEntity {
	
	// Attributes --------------------------------------------------
	
	private String businessName;
	private String banner;
	private String welcomeMessage;
	
	@NotBlank
	public String getBusinessName() {
		return businessName;
	}
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	@URL
	@NotBlank
	public String getBanner() {
		return banner;
	}
	
	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	@NotBlank
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
	
	// Relationships -----------------------------------------------

}
