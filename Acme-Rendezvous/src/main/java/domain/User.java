
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	private Date	dateBorn;


	@Temporal(TemporalType.DATE)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull
	public Date getDateBorn() {
		return this.dateBorn;
	}

	public void setDateBorn(final Date dateBorn) {
		this.dateBorn = dateBorn;
	}


	// Relationships --------------------------------------------------

	private Collection<Comment>		comment;
	private Collection<Rendezvous>	rendezvous;
	private Collection<RSVP>		rsvp;
	private Collection<Question>	question;
	private Collection<Answer>		answer;

	@Valid
	@OneToMany()
	public Collection<Comment> getComment() {
		return this.comment;
	}

	public void setComment(final Collection<Comment> comment) {
		this.comment = comment;
	}
	
	@Valid
	@OneToMany
	public Collection<Rendezvous> getRendezvous() {
		return this.rendezvous;
	}

	public void setRendezvous(final Collection<Rendezvous> rendezvous) {
		this.rendezvous = rendezvous;
	}

	@Valid
	@OneToMany
	public Collection<RSVP> getRsvp() {
		return this.rsvp;
	}

	public void setRsvp(final Collection<RSVP> rsvp) {
		this.rsvp = rsvp;
	}
	
	@Valid
	@OneToMany
	public Collection<Question> getQuestion() {
		return this.question;
	}

	public void setQuestion(final Collection<Question> question) {
		this.question = question;
	}

	@Valid
	@OneToMany
	public Collection<Answer> getAnswer() {
		return answer;
	}

	public void setAnswer(Collection<Answer> answer) {
		this.answer = answer;
	}

}
