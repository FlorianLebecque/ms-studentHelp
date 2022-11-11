package be.ecam.ms_studenthelp.Database.repositories;

import be.ecam.ms_studenthelp.Database.entities.AuthorEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, String> {
    @NotNull List<AuthorEntity> findAll();

    @Override
    @NotNull
    Optional<AuthorEntity> findById(@NotNull String id);
}
