package be.ecam.ms_studenthelp.Database.repositories;

import be.ecam.ms_studenthelp.Database.entities.AuthorEntity;
import be.ecam.ms_studenthelp.Database.entities.PostEntity;
import be.ecam.ms_studenthelp.Database.entities.ReactionEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends CrudRepository<ReactionEntity, Long> {
    @NotNull List<ReactionEntity> findAll();

    @Override
    @NotNull
    Optional<ReactionEntity> findById(@NotNull Long id);
    ReactionEntity findByPost(@NonNull PostEntity post);
    ReactionEntity findByAuthor(@NonNull AuthorEntity author);
    ReactionEntity findByPostAndAuthor(@NonNull PostEntity post, @NonNull AuthorEntity author);
    ReactionEntity findReactionByValue(int value);
    @Transactional
    void deleteAllByPost(@NonNull PostEntity post);
}
