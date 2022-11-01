package be.ecam.ms_studenthelp;

import be.ecam.ms_studenthelp.Database.entities.AuthorEntity;
import be.ecam.ms_studenthelp.Database.entities.PostEntity;
import be.ecam.ms_studenthelp.Database.repositories.AuthorRepository;
import be.ecam.ms_studenthelp.Database.repositories.PostRepository;
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
    @Autowired private PostRepository postRepository;
    @Autowired private AuthorRepository authorRepository;

    /**
     *  ENDPOINT GET post by postId
     *
     *  @param postId Id of the post to get
     *  @return and {@link IPost}
     */
    @GetMapping("/posts/{postId}")
    public IPost GetPostByPostId(@PathVariable("postId") String postId) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        // If the post does not exist, return a 404 error
        if (optionalPostEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found !");
        }

        return optionalPostEntity.get().toPost();
    }


    /**
     * ENDPOINT PATCH to modify content of a post
     *
     * @param postId Id of the post to modify
     * @param body objet post in JSON or just a JSON like {"content": "also works"}
     * @return and {@link IPost}
     */
    @PatchMapping("/posts/{postId}")
    public IPost PatchPostByPostId(@PathVariable("postId") String postId, @RequestBody String body) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        // If the post does not exist, return a 404 error
        if (optionalPostEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found !");
        }

        // Format the body in objet body_data to get attributes
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);
        String content = body_data.get("content").toString();

        // If no content passed, return a bad request error
        if (content == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A content must be set !");
        }

        PostEntity postEntity = optionalPostEntity.get();

        // Update the content
        postEntity.setContent(content);
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
    @PutMapping("/posts/{postId}/answers")
    public IPost ReplyPostByPostId(@PathVariable("postId") String postId, @RequestBody String body) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        // If the post does not exist, return a 404 error
        if (optionalPostEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found !");
        }

        // Parse the JSON information
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);
        String authorId = body_data.get("authorId").toString();
        String content = body_data.get("content").toString();

        // Check if the authorId and content are passed
        if ((content == null) ||(authorId == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Content and authorId must be passed !");
        }

        // Get the author if it exists, otherwise create a new one
        Optional<AuthorEntity>optionalAuthorEntity = authorRepository.findById(authorId);
        AuthorEntity authorEntity = optionalAuthorEntity.isEmpty() ?
                new AuthorEntity(authorId) : optionalAuthorEntity.get();

        PostEntity postEntity = optionalPostEntity.get();
        PostEntity childPostEntity = new PostEntity(content, authorEntity);

        // Save information in the database
        // The post child is saved thanks to cascade.ALL
        childPostEntity.setParent(postEntity);
        authorRepository.save(authorEntity);
        postRepository.save(childPostEntity);

        return postEntity.toPost();
    }

    /**
     * Delete a post
     *
     * @param postId
     * @return
     */
    @DeleteMapping("/posts/{postId}")
    public IPost deletePostByPostId(@PathVariable("postId") String postId) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        // If the post does not exist, return a 404 error
        if (optionalPostEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found !");
        }

        PostEntity postEntity = optionalPostEntity.get();

        // You can't delete the first post, delete the thread instead
        if (postEntity.getParent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You can't delete the first post !");
        }

        postRepository.deleteById(postId);

        return postEntity.toPost();
    }

    @GetMapping("/posts")
    public List<IPost> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();

        // Convert PostEntity to Post
        return postEntities
                .stream()
                .map(PostEntity::toPost)
                .collect(Collectors.toList());
    }
}
