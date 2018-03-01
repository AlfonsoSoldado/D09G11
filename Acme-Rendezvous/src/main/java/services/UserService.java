
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Answer;
import domain.Comment;
import domain.Question;
import domain.RSVP;
import domain.Rendezvous;
import domain.User;
import forms.UserForm;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------
	
	@Autowired
	private UserRepository	userRepository;
	
	@Autowired
	private Validator validator;

	// Constructor ------------------------------------------------------------

	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public User create() {
		User result;
		result = new User();
		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		result.setAnswer(new ArrayList<Answer>());
		result.setComment(new ArrayList<Comment>());
		result.setRendezvous(new ArrayList<Rendezvous>());
		result.setRsvp(new ArrayList<RSVP>());
		result.setQuestion(new ArrayList<Question>());
		authority.setAuthority(Authority.USER);
		userAccount.addAuthority(authority);
		result.setUserAccount(userAccount);
		return result;
	}

	public Collection<User> findAll() {
		Collection<User> result;
		result = this.userRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public User findOne(final int userId) {
		User result;
		result = this.userRepository.findOne(userId);
		return result;
	}

	public User save(final User user) {
		User result = user;
		Assert.notNull(user);
		if (user.getId() == 0) {
			String pass = user.getUserAccount().getPassword();
			final Md5PasswordEncoder code = new Md5PasswordEncoder();
			pass = code.encodePassword(pass, null);
			user.getUserAccount().setPassword(pass);
		}
		result = this.userRepository.save(result);
		return result;
	}

	public void delete(final User user) {
		Assert.notNull(user);
		Assert.isTrue(user.getId() != 0);
		this.userRepository.delete(user);
	}
	
	// Other business method --------------------------------------------------

	public User findByPrincipal() {
		User e;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		e = this.userRepository.findByPrincipal(userAccount.getId());
		return e;
	}

	public User findCreator(final int rendezvousId) {
		User result;
		result = this.userRepository.findCreator(rendezvousId);
		Assert.notNull(result);
		return result;
	}

	public Collection<User> findAttendants(final int rendezvousId) {
		Collection<User> result;
		result = this.userRepository.findAttendants(rendezvousId);
		Assert.notNull(result);
		return result;
	}

	public Collection<User> findRUserSVPbyRendezvous(final int rendezvousId) {
		Collection<User> result;
		result = this.userRepository.findUserRSVPbyRendezvous(rendezvousId);
		Assert.notNull(result);
		return result;
	}

	public User findUserByQuestion(final int questionId) {
		User res;
		res = this.userRepository.findUserByQuestion(questionId);
		return res;
	}
	
	public User findUserByComment(final int commentId) {
		User res;
		res = this.userRepository.findUserByComment(commentId);
		return res;
	}
	
	public User findUserByAnswer(final int answerId) {
		User res;
		res = this.userRepository.findUserByAnswer(answerId);
		return res;
	}

	public boolean checkUserLogged() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("USER");
		if (authority.contains(res))
			result = true;
		return result;
	}

	@SuppressWarnings("deprecation")
	public boolean is18(final User user) {
		boolean result = false;
		final Date fecha = new Date();
		final Integer year = user.getDateBorn().getYear() + 1900;
		final Integer month = user.getDateBorn().getMonth() + 1;
		final Integer day = user.getDateBorn().getDate();
		final Integer y = fecha.getYear() + 1900;
		final Integer m = fecha.getMonth() + 1;
		final Integer d = fecha.getDate();
		if (y - year > 18)
			result = true;
		else if ((y - year == 18) && (m - month > 0))
			result = true;
		else if ((y - year == 18) && (m - month == 0) && (d - day >= 0))
			result = true;
		return result;
	}
	
	public void checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		Authority res = new Authority();
		res.setAuthority("USER");
		Assert.isTrue(authority.contains(res));
	}
	
	public UserForm reconstruct(final UserForm userForm, final BindingResult binding) {
		User res;
		UserForm userFinal = null;
		res = userForm.getUser();
		if (res.getId() == 0) {
			Collection<Comment> comment;
			Collection<Question> question;
			Collection<Rendezvous> rendezvous;
			Collection<RSVP> rsvp;
			UserAccount userAccount;
			Authority authority;
			userAccount = userForm.getUser().getUserAccount();
			authority = new Authority();
			comment = new ArrayList<Comment>();
			question = new ArrayList<Question>();
			rendezvous = new ArrayList<Rendezvous>();
			rsvp = new ArrayList<RSVP>();
			userForm.getUser().setUserAccount(userAccount);
			authority.setAuthority(Authority.USER);
			userAccount.addAuthority(authority);
			userForm.getUser().setComment(comment);
			userForm.getUser().setQuestion(question);
			userForm.getUser().setRendezvous(rendezvous);
			userForm.getUser().setRsvp(rsvp);
			userFinal = userForm;
		} else {
			res = this.userRepository.findOne(userForm.getUser().getId());
			userForm.getUser().setId(res.getId());
			userForm.getUser().setVersion(res.getVersion());
			userForm.getUser().setUserAccount(res.getUserAccount());
			userForm.getUser().setComment(res.getComment());
			userForm.getUser().setQuestion(res.getQuestion());
			userForm.getUser().setRendezvous(res.getRendezvous());
			userForm.getUser().setRsvp(res.getRsvp());
			userFinal = userForm;
		}
		this.validator.validate(userFinal, binding);
		return userFinal;
	}
	
	public User reconstruct(final User user, final BindingResult binding) {
		User res;
		User userFinal;
		if (user.getId() == 0) {
			UserAccount userAccount;
			Authority authority;
			userAccount = user.getUserAccount();
			user.setUserAccount(userAccount);
			authority = new Authority();
			authority.setAuthority(Authority.USER);
			userAccount.addAuthority(authority);
			String password = "";
			password = user.getUserAccount().getPassword();
			user.getUserAccount().setPassword(password);
			userFinal = user;
		} else {
			res = this.userRepository.findOne(user.getId());
			user.setId(res.getId());
			user.setVersion(res.getVersion());
			user.setUserAccount(res.getUserAccount());
			user.getUserAccount().setPassword(user.getUserAccount().getPassword());
			user.getUserAccount().setAuthorities(user.getUserAccount().getAuthorities());
			userFinal = user;
		}
		this.validator.validate(userFinal, binding);
		return userFinal;
	}
}
