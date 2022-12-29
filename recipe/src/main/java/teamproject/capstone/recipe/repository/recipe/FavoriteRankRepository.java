package teamproject.capstone.recipe.repository.recipe;

import java.util.List;

public interface FavoriteRankRepository {
    List<Object[]> findWithRankFavoriteRecipe();
    List<Object[]> findRankFavoriteRecipeByEmail(String email);
    List<Long> findRankFavoriteRecipeByEmailOnlySeq(String email);
}
