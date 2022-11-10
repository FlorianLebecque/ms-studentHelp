package be.ecam.ms_studenthelp;

import be.ecam.ms_studenthelp.Database.entities.AuthorEntity;
import be.ecam.ms_studenthelp.Database.entities.PostEntity;
import be.ecam.ms_studenthelp.Database.repositories.AuthorRepository;
import be.ecam.ms_studenthelp.Database.repositories.PostRepository;
import be.ecam.ms_studenthelp.utils.DatabaseUtils;
import be.ecam.ms_studenthelp.utils.PostBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PostController {
    @Autowired ObjectMapper objectMapper;
    @Autowired private PostRepository postRepository;
    @Autowired private AuthorRepository authorRepository;

    /**
     *  ENDPOINT GET post by postId
     *
     *  @param postId Id of the post to get
     *  @return and {@link IPost}
     */
    @GetMapping(value = "/posts/{postId}", produces="application/json")
    public IPost GetPostByPostId(@PathVariable("postId") String postId) {
        return DatabaseUtils.getPostFromDatabase(postId, postRepository).toPost();
    }


    /**
     * ENDPOINT PATCH to modify content of a post
     *
     * @param postId Id of the post to modify
     * @param body objet post in JSON or just a JSON like {"content": "also works"}
     * @return and {@link IPost}
     */
    @PatchMapping(value = "/posts/{postId}", produces="application/json")
    public IPost PatchPostByPostId(@PathVariable("postId") String postId, @RequestBody String body) {
        PostEntity postEntity = DatabaseUtils.getPostFromDatabase(postId, postRepository);
        PostBody postBody = new PostBody(body);

        // If no content passed, return a bad request error
        if (postBody.getContent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A content must be set !");
        }

        // Update the content
        postEntity.setContent(postBody.getContent());
        postRepository.save(postEntity);

        return postEntity.toPost();
    }

    /**
     *  method to reply to a post
     *
     * @param postId Id of an existing post
     * @param body  The reply post with necessary fields to create a post
     * @return and {@link IPost}
     */
    @PutMapping(value = "/posts/{postId}/answers", produces="application/json")
    public IPost ReplyPostByPostId(@PathVariable("postId") String postId, @RequestBody String body) {
        PostEntity postEntity = DatabaseUtils.getPostFromDatabase(postId, postRepository);
        PostBody postBody = new PostBody(body);

        // Check if the authorId and content are passed
        if ((postBody.getContent() == null) ||(postBody.getAuthorId() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Content and authorId must be passed !");
        }

        // Get the author if it exists, otherwise create a new one
        Optional<AuthorEntity>optionalAuthorEntity = authorRepository.findById(
                postBody.getAuthorId());
        AuthorEntity authorEntity = DatabaseUtils.getAuthorFromDatabase(
                postBody.getAuthorId(), authorRepository);
        PostEntity childPostEntity = new PostEntity(postBody.getContent(), authorEntity);

        // Save information in the database
        // The post child is saved thanks to cascade.ALL
        childPostEntity.setParent(postEntity);
        authorRepository.save(authorEntity);
        postRepository.save(childPostEntity);

        return childPostEntity.toPost();
    }

    /**
     * Delete a post
     *
     * @param postId
     * @return
     */
    @DeleteMapping(value = "/posts/{postId}", produces = "application/json")
    public IPost deletePostByPostId(@PathVariable("postId") String postId) {
        PostEntity postEntity = DatabaseUtils.getPostFromDatabase(postId, postRepository);

        // You can't delete the first post, delete the thread instead
        if (postEntity.getParent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You can't delete the first post !");
        }

        postRepository.deleteById(postId);

        return postEntity.toPost();
    }

    @GetMapping(value = "/posts", produces = "application/json")
    public List<IPost> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();

        // Convert PostEntity to Post
        return postEntities
                .stream()
                .map(PostEntity::toPost)
                .collect(Collectors.toList());
    }
}
