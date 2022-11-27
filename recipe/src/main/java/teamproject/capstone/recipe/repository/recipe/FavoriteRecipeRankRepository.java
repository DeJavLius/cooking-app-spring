package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;

import java.util.List;

public interface FavoriteRecipeRankRepository {
    List<Tuple> findWithRankFavoriteRecipe();
}
