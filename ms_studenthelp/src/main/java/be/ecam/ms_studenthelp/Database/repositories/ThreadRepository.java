package be.ecam.ms_studenthelp.Database.repositories;

import be.ecam.ms_studenthelp.Database.entities.ThreadEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ThreadRepository extends CrudRepository<ThreadEntity, String> {
    @NotNull List<ThreadEntity> findAll();

    @Override
    @NotNull
    Optional<ThreadEntity> findById(@NotNull String id);
    ThreadEntity findByTitle(@NonNull String title);
}
