package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;

import java.util.List;

public interface FavoriteRankRepository {
    List<Tuple> findWithRankFavoriteRecipe();
    List<Long> findAllFavoriteRecipe(String email);
}
