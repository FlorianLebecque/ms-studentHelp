package be.ecam.ms_studenthelp.Database.repositories;

import java.util.List;
import java.util.Optional;

import be.ecam.ms_studenthelp.Database.entities.CategoryEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    @NotNull List<CategoryEntity> findAll();
    @NotNull Optional<CategoryEntity> findById(@NonNull long id);
    @NotNull Optional<CategoryEntity> findByTitle(@NonNull String title);
    boolean existsByTitle(@NonNull String title);
}
