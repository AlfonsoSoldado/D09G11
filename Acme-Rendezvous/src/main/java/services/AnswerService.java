package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AnswerRepository;
import domain.Answer;
import domain.User;

@Service
@Transactional
public class AnswerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnswerRepository answerRepository;

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService userService;

	// Constructor ------------------------------------------------------------

	public AnswerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Answer create() {
		userService.checkAuthority();
		Answer result;
		result = new Answer();
		return result;
	}

	public Collection<Answer> findAll() {
		Collection<Answer> result;
		result = this.answerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Answer findOne(final int answerId) {
		Answer result;
		result = this.answerRepository.findOne(answerId);
		return result;
	}

	public Answer save(final Answer answer) {
		userService.checkAuthority();
		Answer result = answer;
		Assert.notNull(answer);
		result = this.answerRepository.save(result);
		this.updateUser(result);
		return result;
	}

	public void delete(final Answer answer) {
		Assert.notNull(answer);
		Assert.isTrue(answer.getId() != 0);
		this.answerRepository.delete(answer);
	}

	// Other business method --------------------------------------------------

	public Collection<Answer> findAnswersByQuestion(int id) {
		Collection<Answer> res;
		res = this.answerRepository.findAnswersByQuestion(id);
		return res;
	}
	
	private void updateUser(Answer answer) {
		User user;
		user = userService.findByPrincipal();
		
		Collection<Answer> answers = new ArrayList<Answer>();
		answers = user.getAnswer();
		
		answers.add(answer);
		user.setAnswer(answers);
		
		userService.save(user);
	}

}
