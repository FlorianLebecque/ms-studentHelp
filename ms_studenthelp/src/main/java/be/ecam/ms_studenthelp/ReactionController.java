package be.ecam.ms_studenthelp;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ecam.ms_studenthelp.Object.Post;
import be.ecam.ms_studenthelp.Object.Reaction;

@RestController
public class ReactionController {

	@GetMapping("/posts/{postId}/reactions")
	public GetPostsPostIdReactionsResult getPostsPostIdReactions(@PathVariable("postId") String postId) {
		Reaction r1 = new Reaction(postId, "efgh", 1);
		Reaction r2 = new Reaction(postId, "ijkl", -1);
		ArrayList r = new ArrayList<Reaction>();
		r.add(r1);
		r.add(r2);
		return new GetPostsPostIdReactionsResult(r);
	}

	class GetPostsPostIdReactionsResult {
		public ArrayList<Reaction> data;

		public GetPostsPostIdReactionsResult(ArrayList<Reaction> data) {
			this.data = data;
		}
	}

	@PutMapping("/posts/{postId}/reactions")
	public PutPostsPostIdReactionsResult putPostsPostIdReactions(@PathVariable("postId") String postId) {
		Reaction r = new Reaction(postId, "abcd", 1);
		return new PutPostsPostIdReactionsResult(r);
	}

	class PutPostsPostIdReactionsResult {
		public Reaction data;

		public PutPostsPostIdReactionsResult(Reaction data) {
			this.data = data;
		}
	}

	@DeleteMapping("/posts/{postId}/reactions")
	public ResponseEntity deletePostsPostIdReactions(@PathVariable("postId") String postId) {
		// Delete reaction
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
