package be.ecam.ms_studenthelp;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public PutPostsPostIdReactionsResult putPostsPostIdReactions(@PathVariable("postId") String postId, @RequestBody String body, HttpServletResponse resp) {
        try {
            UUID.fromString(postId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "postId should be an UUID");
        }

        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);
        Integer value = (Integer) body_data.get("value");
        if (value == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "value should be in the body");
        }
        if (value != 1 && value != -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "value should be either 1 or -1");
        }


        IIODatabaseObject db = MsStudenthelpApplication.DatabaseManager;
        IPost post = db.GetPost(postId);
        if (post == null) {
            resp.setStatus(404);
            return new PutPostsPostIdReactionsResult("Post not found");
        }

        String authorId = "author";

        IReaction reaction = db.GetReaction(post, authorId);
        if (reaction == null) {
            reaction = new Reaction(postId, authorId, value);
            reaction = db.CreateReaction(reaction);
            if (reaction == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
            }
        } else {
            reaction.setValue(value);
            reaction = db.UpdateReaction(reaction);
            if (reaction == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
            }
        }

		return new PutPostsPostIdReactionsResult(reaction);
	}

	class PutPostsPostIdReactionsResult {
		public IReaction data = null;
		public String message = null;

		public PutPostsPostIdReactionsResult(IReaction data) {
			this.data = data;
		}

		public PutPostsPostIdReactionsResult(String message) {
			this.message = message;
		}
	}

	@DeleteMapping("/posts/{postId}/reactions")
	public ResponseEntity deletePostsPostIdReactions(@PathVariable("postId") String postId, HttpServletResponse resp) {

        IIODatabaseObject db = MsStudenthelpApplication.DatabaseManager;
        IPost post = db.GetPost(postId);
        if (post == null) {
            resp.setStatus(404);
            return new PutPostsPostIdReactionsResult("Post not found");
        }

        // TODO: Fetch the author from the OAuth token
        String authorId = "author";

        // Creating or updating the reaction depending on its existence
        IReaction reaction = db.GetReaction(post, authorId);
        if (reaction != null) {
            db.DeleteReaction(reaction);
        }

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
