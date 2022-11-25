package be.ecam.ms_studenthelp;

import be.ecam.ms_studenthelp.Database.entities.*;
import be.ecam.ms_studenthelp.Database.repositories.AuthorRepository;
import be.ecam.ms_studenthelp.Database.repositories.CategoryRepository;
import be.ecam.ms_studenthelp.Database.repositories.TagRepository;
import be.ecam.ms_studenthelp.Database.repositories.ThreadRepository;
import be.ecam.ms_studenthelp.Object.*;
import be.ecam.ms_studenthelp.utils.DatabaseUtils;
import be.ecam.ms_studenthelp.utils.ForumTagBody;
import be.ecam.ms_studenthelp.utils.ForumThreadBody;
import be.ecam.ms_studenthelp.utils.PostBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
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
        ForumThreadBody forumThreadBody = new ForumThreadBody(body);

        // If title, tags or category is not specified, return a 3xx error
        if ((forumThreadBody.getTitle() == null) || (forumThreadBody.getTags() == null) ||
                (forumThreadBody.getCategory() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad parameters !");
        }

        CategoryEntity categoryEntity = DatabaseUtils.getCategoryFromDatabase(
                forumThreadBody.getCategory(), categoryRepository);

        // Check if the first post is defined
        if (forumThreadBody.getFirstPost() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A first post must be defined !");
        }

        IPost firstPost = postFromPostBody(forumThreadBody.getFirstPost());

        // Check if the first post is defined
        if (firstPost == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A first post must be defined !");
        }

        AuthorEntity authorEntity = DatabaseUtils.getAuthorFromDatabase(
                firstPost.getAuthor().getId(),
                authorRepository);
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

        ThreadEntity threadEntity = new ThreadEntity(
                forumThreadBody.getTitle(),
                categoryEntity,
                postEntity);
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
        return DatabaseUtils.getForumThreadFromDatabase(threadId, threadRepository)
                .toForumThread();
    }

    /**
     * PATCH /threads/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/patch-threads-threadId
     * Modify an existing Thread from its threadId and a body (where the modification are) and return it.
     * It allows to modify the title, tags, category and/or answered of an existing thread.
     */
    @PatchMapping("/threads/{threadId}")
    public IForumThread patchThreadsThreadId(@PathVariable("threadId") String threadId, @RequestBody String body) {
        ThreadEntity threadEntity = DatabaseUtils.getForumThreadFromDatabase(threadId,
                threadRepository);
        ForumThreadBody forumThreadBody = new ForumThreadBody(body);

        // Set new values for the current thread
        if (forumThreadBody.getTitle() != null) {
            threadEntity.setTitle(forumThreadBody.getTitle());
        }
        if (forumThreadBody.getTags() != null) {
            Set<TagEntity> tagEntities = new HashSet<>();

            for (String tag : forumThreadBody.getTags()) {
                TagEntity tagEntity = tagRepository.findByTitleAndThread(tag, threadEntity);

                tagEntities.add(tagEntity != null ? tagEntity : new TagEntity(tag, threadEntity));
            }

            threadEntity.setTags(tagEntities);
        }

        if (forumThreadBody.getCategory() != null) {
            threadEntity.setCategory(DatabaseUtils.getCategoryFromDatabase(
                    forumThreadBody.getCategory(),
                    categoryRepository));
        }
        threadEntity.setAnswered(forumThreadBody.isAnswered());

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
        ThreadEntity threadEntity = DatabaseUtils.getForumThreadFromDatabase(
                threadId,
                threadRepository);

        threadRepository.deleteById(threadEntity.getId());
        return threadEntity.toForumThread();
    }



    /**
     * GET /threads
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/get-threads
     * Get a list of published threads
     */
    @GetMapping("/threads")
    public List<ForumThread> GetForumThreadPages() {
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


    /**
     * GET /threads/tags/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/get-tags
     * Get a list of the tags linked to a specific thread
     */
    @GetMapping("/threads/{threadId}/tags")
    public Set<Tag> getThreadsThreadIdTags(@PathVariable("threadId") String threadId) {

        return DatabaseUtils.getForumThreadFromDatabase(threadId, threadRepository).toForumThread().getTags();
    }


    /**
     * POST /threads/{Title}/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/post-tag
     * Post a new tag to a specific thread
     */
    @PostMapping("/threads/{threadId}/tags")
    public Set<Tag> postTagThreadId(@PathVariable("threadId") String threadId, @RequestBody String body) {
        ThreadEntity threadEntity = DatabaseUtils.getForumThreadFromDatabase(threadId, threadRepository);
        ForumTagBody forumTagBody = ForumTagBody.fromBody(body);
        String tag = forumTagBody.getTag();

        if (tag != null) {
            TagEntity tagEntity = new TagEntity(tag, threadEntity);

            threadEntity.addTag(tagEntity);
            threadRepository.save(threadEntity);

        } else if (forumTagBody.getTag() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No tag mentioned !");
        }

        return threadEntity.toForumThread().getTags();
    }


    /**
     * DELETE /threads/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/delete-threads
     * Delete a tag from a specific thread.
     */
    @DeleteMapping("/threads/{threadId}/tags/{tagtitle}")
    public Set<Tag> DeleteTagFromThread(@PathVariable("tagtitle") String tagtitle ,@PathVariable("threadId") String threadId) {

        ThreadEntity threadEntity = DatabaseUtils.getForumThreadFromDatabase(threadId, threadRepository);
        TagEntity tag = tagRepository.findByTitleAndThread(tagtitle, threadEntity);
        tagRepository.deleteById(tag.getId());

        return threadEntity.toForumThread().getTags();

    }





    private static IPost postFromPostBody(@NonNull PostBody postBody) {
        if ((postBody.getContent() == null) || (postBody.getAuthorId() == null)) {
            return null;
        }

        return new Post(postBody.getContent(), new Author(postBody.getAuthorId()), null);
    }
}
