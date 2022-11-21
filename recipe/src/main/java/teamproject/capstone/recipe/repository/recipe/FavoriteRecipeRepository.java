package teamproject.capstone.recipe.repository.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.recipe.FavoriteRecipeEntity;

@Repository
public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipeEntity, Long> {
}
