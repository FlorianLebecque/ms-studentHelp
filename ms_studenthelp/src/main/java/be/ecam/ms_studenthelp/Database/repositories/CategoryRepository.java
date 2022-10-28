package be.ecam.ms_studenthelp.Database.repositories;

import java.util.List;

import be.ecam.ms_studenthelp.Database.entities.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    @NotNull List<Category> findAll();
    Category findById(long id);
    Category findByTitle(String title);
}
