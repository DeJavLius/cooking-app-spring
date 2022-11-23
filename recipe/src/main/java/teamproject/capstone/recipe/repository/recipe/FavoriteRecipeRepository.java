package teamproject.capstone.recipe.repository.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;
import teamproject.capstone.recipe.entity.recipe.FavoriteRecipeEntity;
import teamproject.capstone.recipe.entity.user.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipeEntity, Long> {
    Optional<List<FavoriteRecipeEntity>> findByUserEmail(String email);
    Optional<List<FavoriteRecipeEntity>> findByRecipeSeq(long recipeSeq);
    Optional<FavoriteRecipeEntity> findByRecipeSeqAndUserEmail(long recipeSeq, String userEmail);
    void deleteByUserEmail(String email);
}
