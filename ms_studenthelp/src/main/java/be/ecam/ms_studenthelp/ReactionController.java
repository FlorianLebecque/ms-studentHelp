package be.ecam.ms_studenthelp;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import be.ecam.ms_studenthelp.Database.IIODatabaseObject;
import be.ecam.ms_studenthelp.Object.Reaction;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Interfaces.IReaction;

@RestController
public class ReactionController {



	@GetMapping("/posts/{postId}/reactions")
	public GetPostsPostIdReactionsResult getPostsPostIdReactions(@PathVariable("postId") String postId, HttpServletResponse resp) {

        try {
            UUID.fromString(postId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "postId should be an UUID");
        }

        IIODatabaseObject db = MsStudenthelpApplication.DatabaseManager;
        IPost post = db.GetPost(postId);
        if (post == null) {
            resp.setStatus(404);
            return new GetPostsPostIdReactionsResult("Post not found");
        }
        List<IReaction> reactions = db.GetReactions(post);
        if (reactions == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }

		return new GetPostsPostIdReactionsResult(reactions);
	}

	class GetPostsPostIdReactionsResult {
		public List<IReaction> data = null;
		public String message = null;

		public GetPostsPostIdReactionsResult(List<IReaction> data) {
			this.data = data;
		}

		public GetPostsPostIdReactionsResult(String message) {
			this.message = message;
		}
	}

	@PutMapping("/posts/{postId}/reactions")
	public PutPostsPostIdReactionsResult putPostsPostIdReactions(@PathVariable("postId") String postId) {
		Reaction r = new Reaction(postId, "abcd", 1);
		return new PutPostsPostIdReactionsResult(r);
	}

	class PutPostsPostIdReactionsResult {
		public IReaction data;

		public PutPostsPostIdReactionsResult(IReaction data) {
			this.data = data;
		}
	}

	@DeleteMapping("/posts/{postId}/reactions")
	public ResponseEntity deletePostsPostIdReactions(@PathVariable("postId") String postId) {
		// Delete reaction
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
