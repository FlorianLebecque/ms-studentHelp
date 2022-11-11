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
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.utils.DatabaseUtils;
import be.ecam.ms_studenthelp.utils.ReactionBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        PostEntity postEntity = DatabaseUtils.getPostFromDatabase(postId, postRepository);
        Set<ReactionEntity> reactionEntities = postEntity.getReactions();

        // Convert ReactionEntity to Reaction
        return reactionEntities
                .stream()
                .map(ReactionEntity::toReaction)
                .collect(Collectors.toSet());
	}

	@PutMapping("/posts/{postId}/reactions")
	public Reaction putPostsPostIdReactions(@PathVariable("postId") String postId, @RequestBody String body, HttpServletResponse resp) {
        PostEntity postEntity = DatabaseUtils.getPostFromDatabase(postId, postRepository);
        ReactionBody reactionBody = new ReactionBody(body);

        if ((reactionBody.getValue() == null) || (reactionBody.getAuthorId() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Value and authorId should be presents in the body !");
        }
        if ((reactionBody.getValue() != 1) && (reactionBody.getValue() != -1)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Value should be 1 or -1 !");
        }

        AuthorEntity authorEntity = DatabaseUtils.getAuthorFromDatabase(
                reactionBody.getAuthorId(),
                authorRepository);
        ReactionEntity reactionEntity = reactionRepository.findByPostAndAuthor(postEntity,
                authorEntity);

        // If the author has already reacted to this post, update his reaction
        if (reactionEntity == null) {
            reactionEntity = new ReactionEntity(reactionBody.getValue(), authorEntity, postEntity);
        } else {
            reactionEntity.setValue(reactionBody.getValue());
        }

        // Save the author if created
        authorRepository.save(authorEntity);
        reactionRepository.save(reactionEntity);

        return reactionEntity.toReaction();
	}

	@DeleteMapping("/posts/{postId}/reactions")
	public IPost deletePostsPostIdReactions(@PathVariable("postId") String postId) {
        PostEntity postEntity = DatabaseUtils.getPostFromDatabase(postId, postRepository);

        reactionRepository.deleteAllByPost(postEntity);
        return postEntity.toPost();
	}
}
