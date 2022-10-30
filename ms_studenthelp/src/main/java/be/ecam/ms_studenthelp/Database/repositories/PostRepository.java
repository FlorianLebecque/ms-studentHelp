package be.ecam.ms_studenthelp.Database.repositories;

import be.ecam.ms_studenthelp.Database.entities.PostEntity;
import be.ecam.ms_studenthelp.Object.Post;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, String> {
    @Override
    @NotNull
    Optional<PostEntity> findById(@NotNull String id);
    PostEntity findByParent(@Nullable PostEntity parent);
    PostEntity findAllByParent(@Nullable PostEntity parent);

    @Override
    void deleteAll(@NotNull Iterable<? extends PostEntity> entities);
}
