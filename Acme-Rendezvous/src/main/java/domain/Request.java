package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Request {

	// Attributes -------------------------------------------------------------

	private CreditCard creditCard;
	private String comment;

	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	// Relationships --------------------------------------------------
	
	

}
