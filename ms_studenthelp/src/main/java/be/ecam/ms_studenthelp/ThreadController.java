package be.ecam.ms_studenthelp;

import be.ecam.ms_studenthelp.Database.entities.*;
import be.ecam.ms_studenthelp.Database.repositories.AuthorRepository;
import be.ecam.ms_studenthelp.Database.repositories.CategoryRepository;
import be.ecam.ms_studenthelp.Database.repositories.TagRepository;
import be.ecam.ms_studenthelp.Database.repositories.ThreadRepository;
import be.ecam.ms_studenthelp.Object.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class ThreadController {
    private @Autowired ThreadRepository threadRepository;
    private @Autowired CategoryRepository categoryRepository;
    private @Autowired AuthorRepository authorRepository;
    private @Autowired TagRepository tagRepository;

    /**
     * POST /threads
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/post-threads
     * Create a new thread in a specific category and the post it contain. Return a IForumThread
     * Parameters of the body :
     * String title
     * List<String> tags
     * String category
     * Boolean answered
     * Map<String,String> first_post : to create the content of the Thread
     */
    @PostMapping("/threads")
    public IForumThread PostThreads(@RequestBody String body){
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);

        // ForumThread info
        String title = (String) body_data.get("title");
        List<String> tags = new ArrayList<>(); // (List<String>) body_data.get("tags");
        String category = (String) body_data.get("category");
        Boolean answered = (Boolean) body_data.get("answered");

        // If title, tags or category is not specified, return a 3xx error
        if ((title == null) || (tags == null) || (category == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad parameters !");
        }

        if (answered == null) {
            answered = false;
        }

        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findByTitle(category);

        if (optionalCategoryEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The category %s does not exists !", category));
        }

        CategoryEntity categoryEntity = optionalCategoryEntity.get();

        // Check if the first post is defined
        if (!(body_data.get("firstPost") instanceof Map)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A first post must be defined !");
        }

        IPost firstPost = postFromJson((HashMap<String, String>) body_data.get("firstPost"));

        if (firstPost == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A first post must be defined !");
        }

        Optional<AuthorEntity> optionalAuthorEntity = authorRepository.findById(firstPost.getAuthor().getId());
        AuthorEntity authorEntity = optionalAuthorEntity.isEmpty() ?
                new AuthorEntity(firstPost.getAuthor().getId()) : optionalAuthorEntity.get();

        PostEntity postEntity = new PostEntity(
                firstPost.getContent(),
                firstPost.getUpvotes(),
                firstPost.getDownvotes(),
                firstPost.getDatePosted(),
                firstPost.getDateModified(),
                null, // No child when the thread is created
                authorEntity,
                new HashSet<>()
        );

        ThreadEntity threadEntity = new ThreadEntity(title, categoryEntity, postEntity);
        authorRepository.save(authorEntity);
        threadRepository.save(threadEntity);

        return threadEntity.toForumThread();
    }

    /**
     * GET /threads/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/get-threads-threadId
     * Return an existing Thread from is threadID (String)
     */
    @GetMapping("/threads/{threadId}")
    public IForumThread getThreadsThreadId(@PathVariable("threadId") String threadId) {
        Optional<ThreadEntity> optionalThreadEntity = threadRepository.findById(threadId);

        // Return a 404 error if the thread does not exist
        if (optionalThreadEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Thread %s not found !", threadId));
        }

        return optionalThreadEntity.get().toForumThread();
    }

    /**
     * PATCH /threads/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/patch-threads-threadId
     * Modify an existing Thread from its threadId and a body (where the modification are) and return it.
     * It allows to modify the title, tags, category and/or answered of an existing thread.
     */
    @PatchMapping("/threads/{threadId}")
    public IForumThread patchThreadsThreadId(@PathVariable("threadId") String threadId, @RequestBody String body) {
        Optional<ThreadEntity> optionalThreadEntity = threadRepository.findById(threadId);

        // If the thread does not exist, return a 404 error
        if (optionalThreadEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Thread %s not found !", threadId));
        }

        ThreadEntity threadEntity = optionalThreadEntity.get();
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);

        // ForumThread info
        String title = (String) body_data.get("title");
        List<String> tags = (List<String>) body_data.get("tags");
        String category = (String) body_data.get("category");
        Boolean answered = (Boolean) body_data.get("answered");

        // Set new values for the current thread
        if (title != null) {
            threadEntity.setTitle(title);
        }
        if (tags != null) {
            Set<TagEntity> tagEntities = new HashSet<>();

            for (String tag : tags) {
                TagEntity tagEntity = tagRepository.findByTitleAndThread(tag, threadEntity);

                tagEntities.add(tagEntity != null ? tagEntity : new TagEntity(tag, threadEntity));
            }

            threadEntity.setTags(tagEntities);
        }

        if (category != null) {
            Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findByTitle(category);

            if (optionalCategoryEntity.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("The category %s does not exists !", category));
            }

            threadEntity.setCategory(optionalCategoryEntity.get());
        }

        if (answered != null) {
            threadEntity.setAnswered(answered);
        }

        threadRepository.save(threadEntity);
        return threadEntity.toForumThread();
    }

    /**
     * DELETE /threads/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/delete-threads
     * Delete a Thread from is Id.
     */
    @DeleteMapping("/threads/{threadId}")
    public IForumThread DeleteForumThreadTitle(@PathVariable("threadId") String threadId) {
        Optional<ThreadEntity> optionalThreadEntity = threadRepository.findById(threadId);

        // If the thread does not exist, return a 404 error
        if (optionalThreadEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Thread %s not found !", threadId));
        }

        // Delete the thread from the database
        ThreadEntity threadEntity = optionalThreadEntity.get();
        threadRepository.deleteById(optionalThreadEntity.get().getId());

        return threadEntity.toForumThread();
    }

    /*
     * GET /threads
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/get-threads
     * Get a list of published threads
     */
    @GetMapping("/threads")
    public List<IForumThread> GetForumThreadPages() {
        List<ThreadEntity> threadEntities = threadRepository.findAll();

        // Convert all the ThreadEntity to ForumThread
        return threadEntities
                .stream()
                .map(object -> new ForumThread(
                        object.getId(),
                        object.getTitle(),
                        object.getAnswered(),
                        object.getCategory().toCategory(),
                        object.getDatePosted(),
                        object.getDateModified(),
                        object.getFirstPost().toPost(),
                        object.getTags()
                                .stream()
                                .map(tag -> new Tag(tag.getId(), tag.getTitle()))
                                .collect(Collectors.toSet()))
                ).collect(Collectors.toList());
    }

    private static IPost postFromJson(@NonNull Map<String, String> jsonPost) {
        String content = jsonPost.get("content");
        String authorId = jsonPost.get("authorId");

        if ((content == null) || (authorId == null)) {
            return null;
        }

        return new Post(content, new Author(authorId), null);
    }
}
