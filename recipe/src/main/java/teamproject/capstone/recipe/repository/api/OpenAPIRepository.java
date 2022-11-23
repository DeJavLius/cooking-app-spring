package teamproject.capstone.recipe.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;

import java.util.Optional;

@Repository
public interface OpenAPIRepository extends JpaRepository<OpenRecipeEntity, Long> {
    Optional<OpenRecipeEntity> findByRcpSeq(Long recipeSeq);
}
