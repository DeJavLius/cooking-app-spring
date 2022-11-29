package teamproject.capstone.recipe.repository.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;

import java.util.Optional;

@Repository
public interface OpenRecipeRepository extends JpaRepository<OpenRecipeEntity, Long> {
    Optional<OpenRecipeEntity> findByRcpSeq(Long recipeSeq);
}
