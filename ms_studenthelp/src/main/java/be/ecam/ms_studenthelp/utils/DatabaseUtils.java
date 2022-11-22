package be.ecam.ms_studenthelp.utils;
import java.util.*;

import be.ecam.ms_studenthelp.Database.entities.AuthorEntity;
import be.ecam.ms_studenthelp.Database.entities.CategoryEntity;
import be.ecam.ms_studenthelp.Database.entities.PostEntity;
import be.ecam.ms_studenthelp.Database.entities.ThreadEntity;
import be.ecam.ms_studenthelp.Database.entities.TagEntity;
import be.ecam.ms_studenthelp.Database.repositories.AuthorRepository;
import be.ecam.ms_studenthelp.Database.repositories.CategoryRepository;
import be.ecam.ms_studenthelp.Database.repositories.PostRepository;
import be.ecam.ms_studenthelp.Database.repositories.ThreadRepository;
import be.ecam.ms_studenthelp.Database.repositories.TagRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class DatabaseUtils {
    public static ThreadEntity getForumThreadFromDatabase(String threadId,
                                                           ThreadRepository threadRepository) {
        Optional<ThreadEntity> optionalThreadEntity = threadRepository.findById(threadId);

        // If the thread does not exist, return a 404 error
        if (optionalThreadEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Thread %s not found !", threadId));
        }

        return optionalThreadEntity.get();
    }

    public static PostEntity getPostFromDatabase(String postId,
                                                 @NonNull PostRepository postRepository) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(postId);

        // If the post does not exist, return a 404 error
        if (optionalPostEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found !");
        }

        return optionalPostEntity.get();
    }

    public static CategoryEntity getCategoryFromDatabase(String category,
                                                         @NonNull CategoryRepository categoryRepository) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findByTitle(category);

        if (optionalCategoryEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The category %s does not exists !", category));
        }

        return optionalCategoryEntity.get();
    }

    public static AuthorEntity getAuthorFromDatabase(String authorId,
                                                     @NonNull AuthorRepository authorRepository) {
        Optional<AuthorEntity> optionalAuthorEntity = authorRepository.findById(authorId);

        if (optionalAuthorEntity.isEmpty()) {
            return new AuthorEntity(authorId);
        }

        return optionalAuthorEntity.get();
    }
}
