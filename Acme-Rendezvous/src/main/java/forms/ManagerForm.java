package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Manager;

public class ManagerForm {
	
	@Valid
	private Manager manager;
	private String confirmPassword;
	private Boolean terms;
	
	public ManagerForm() {
		super();
	}

	public ManagerForm(final Manager manager) {
		this.manager = manager;
		this.confirmPassword = "";
		this.terms = false;
	}
	
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@NotNull
	public Boolean getTerms() {
		return terms;
	}
	public void setTerms(Boolean terms) {
		this.terms = terms;
	}

}
