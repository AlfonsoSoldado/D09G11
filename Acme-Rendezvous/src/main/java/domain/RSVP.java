
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "rendezvous_id") })
public class RSVP extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private boolean	confirmed;

	public boolean getConfirmed() {
		return this.confirmed;
	}

	public void setConfirmed(final boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	// Relationships ----------------------------------------------------------

	private Rendezvous rendezvous;
	
	@Valid
	@ManyToOne(optional = false)
	public Rendezvous getRendezvous() {
		return rendezvous;
	}

	public void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}

}
