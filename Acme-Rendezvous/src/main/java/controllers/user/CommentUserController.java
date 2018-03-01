
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Comment;
import domain.Rendezvous;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CommentService		commentService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Constructors ---------------------------------------------------------

	public CommentUserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvousId) {
		ModelAndView result;
		if (this.rendezvousService.findOne(rendezvousId) == null)
			result = new ModelAndView("redirect:../../");
		else {
			Collection<Comment> comment;

			comment = this.commentService.findCommentsByRendezvous(rendezvousId);

			result = new ModelAndView("comment/list");
			result.addObject("comment", comment);
			result.addObject("requestURI", "comment/user/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/listReplies", method = RequestMethod.GET)
	public ModelAndView listReplies(@RequestParam final int commentId) {
		ModelAndView result;
		Collection<Comment> comment;

		final Comment c = this.commentService.findOne(commentId);

		comment = c.getReplies();

		result = new ModelAndView("comment/list");
		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int rendezvousId) {
		ModelAndView res;

		if (this.rendezvousService.findOne(rendezvousId) == null)
			res = new ModelAndView("redirect:../../");
		else {
			final Comment comment = this.commentService.create();
			comment.setRendezvous(this.rendezvousService.findOne(rendezvousId));
			res = this.createEditModelAndView(comment);
		}
		return res;

	}

	@RequestMapping(value = "/createReply", method = RequestMethod.GET)
	public ModelAndView createReply(@RequestParam final int commentId) {
		ModelAndView res;
		if (this.commentService.findOne(commentId) == null)
			res = new ModelAndView("redirect:../../");
		else {
			Comment result;

			Comment comment;

			comment = this.commentService.findOne(commentId);

			result = this.commentService.create();
			result.setParent(comment);
			result.setRendezvous(comment.getRendezvous());
			res = this.createEditModelAndView(result);
		}
		return res;
	}
	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;

		comment = this.commentService.findOne(commentId);
		result = this.createEditModelAndView(comment);

		return result;
	}

	// Saving --------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndView(comment, "comment.params.error");
		else
			try {
				final Comment save = this.commentService.create();
				save.setText(comment.getText());
				save.setPicture(comment.getPicture());
				Comment padre = null;
				if (comment.getParent() != null) {
					final int id = comment.getParent().getId();
					padre = this.commentService.findOne(id);
					save.setParent(padre);
				}

				final Rendezvous rendezvous = this.rendezvousService.findOne(comment.getRendezvous().getId());
				save.setRendezvous(rendezvous);
				this.commentService.save(save);

				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(comment, "comment.commit.error");
			}
		return res;
	}

	// Deleting --------------------------------------------------------------

	// Ancillary methods --------------------------------------------------

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("message", message);
		result.addObject("requestUri", "comment/user/edit.do");

		return result;
	}
}
