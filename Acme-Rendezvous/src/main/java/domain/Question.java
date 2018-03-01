
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "rendezvous_id") })
public class Question extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	text;

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	//Relationships -----------------------------------------------------------

	private Collection<Answer> answer;
	private Rendezvous rendezvous;
	
	@Valid
	@ManyToOne(optional=true)
	public Rendezvous getRendezvous() {
		return rendezvous;
	}

	public void setRendezvous(Rendezvous rendezvous) {
		this.rendezvous = rendezvous;
	}

	@Valid
	@OneToMany(mappedBy = "question")
	public Collection<Answer> getAnswer() {
		return answer;
	}

	public void setAnswer(Collection<Answer> answer) {
		this.answer = answer;
	}

}
