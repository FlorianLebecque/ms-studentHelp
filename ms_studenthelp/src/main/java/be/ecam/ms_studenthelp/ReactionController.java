package be.ecam.ms_studenthelp;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import be.ecam.ms_studenthelp.Database.entities.AuthorEntity;
import be.ecam.ms_studenthelp.Database.entities.PostEntity;
import be.ecam.ms_studenthelp.Database.entities.ReactionEntity;
import be.ecam.ms_studenthelp.Database.repositories.AuthorRepository;
import be.ecam.ms_studenthelp.Database.repositories.PostRepository;
import be.ecam.ms_studenthelp.Database.repositories.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import be.ecam.ms_studenthelp.Object.Reaction;

@RestController
public class ReactionController {
    @Autowired private ReactionRepository reactionRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private AuthorRepository authorRepository;

	@GetMapping("/posts/{postId}/reactions")
	public Set<Reaction> getPostsPostIdReactions(@PathVariable("postId") String postId, HttpServletResponse resp) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        // If the post is not found, return a 404 error
        if (optionalPostEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found !");
        }

        PostEntity postEntity = optionalPostEntity.get();
        Set<ReactionEntity> reactionEntities = postEntity.getReactions();

        // Convert ReactionEntity to Reaction
        return reactionEntities
                .stream()
                .map(ReactionEntity::toReaction)
                .collect(Collectors.toSet());
	}

	@PutMapping("/posts/{postId}/reactions")
	public Reaction putPostsPostIdReactions(@PathVariable("postId") String postId, @RequestBody String body, HttpServletResponse resp) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        // If the post is not found, return a 404 error
        if (optionalPostEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found !");
        }

        // Parsing the JSON body
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);

        // Getting `value` from the parsed body, and validating it
        Integer value = (Integer) body_data.get("value");
        String authorId = (String) body_data.get("authorId");

        if ((value == null) || (authorId == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value and authorId should be presents in the body !");
        }
        if ((value != 1) && (value != -1)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value should be 1 or -1 !");
        }

        PostEntity postEntity = optionalPostEntity.get();
        Optional<AuthorEntity> optionalAuthorEntity = authorRepository.findById(authorId);
        AuthorEntity authorEntity = optionalAuthorEntity.isEmpty() ? new AuthorEntity(authorId) :
                optionalAuthorEntity.get();
        ReactionEntity reactionEntity = reactionRepository.findByPostAndAuthor(postEntity,
                authorEntity);

        // If the author has already reacted to this post, update his reaction
        if (reactionEntity == null) {
            reactionEntity = new ReactionEntity(value, authorEntity, postEntity);
        } else {
            reactionEntity.setValue(value);
        }

        // Save the author if created
        authorRepository.save(authorEntity);
        reactionRepository.save(reactionEntity);

        return reactionEntity.toReaction();
	}

	@DeleteMapping("/posts/{postId}/reactions")
	public ResponseEntity deletePostsPostIdReactions(@PathVariable("postId") String postId) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        // If the post is not found, return a 404 error
        if (optionalPostEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found !");
        }

        PostEntity postEntity = optionalPostEntity.get();
        reactionRepository.deleteAllByPost(postEntity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
