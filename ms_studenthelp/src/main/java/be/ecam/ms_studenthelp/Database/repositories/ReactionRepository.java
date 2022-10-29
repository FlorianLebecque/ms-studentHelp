package be.ecam.ms_studenthelp.Database.repositories;

import be.ecam.ms_studenthelp.Database.entities.ReactionEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends CrudRepository<ReactionEntity, String> {
    @NotNull List<ReactionEntity> findAll();
    ReactionEntity findByPostIdAndAuthorId(String postId, String authorId);
    ReactionEntity findByPostId(String postId);
    ReactionEntity findReactionByAuthorId(String authorId);
    ReactionEntity findReactionByValue(int value);
}
