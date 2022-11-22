package be.ecam.ms_studenthelp.Database.repositories;

import be.ecam.ms_studenthelp.Database.entities.TagEntity;
import be.ecam.ms_studenthelp.Database.entities.ThreadEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Long> {
    @NotNull List<TagEntity> findAll();
    @NotNull Optional<TagEntity> findById(@NonNull long id);
    TagEntity findByTitle(@NonNull String title);
    TagEntity findByThread(@NonNull String threadId);
    TagEntity findByTitleAndThread(@NonNull String title, @NonNull ThreadEntity thread);
    boolean existsByTitleAndThread(@NonNull String title, @NonNull ThreadEntity thread);
}
