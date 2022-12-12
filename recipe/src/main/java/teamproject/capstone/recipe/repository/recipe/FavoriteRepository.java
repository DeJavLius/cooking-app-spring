package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;

import java.util.List;

public interface FavoriteRepository {
    List<Object[]> findFavoriteByRecipeSeq(Long recipeSeq);
    List<Object[]> findFavoriteByEmail(String email);
    List<Object[]> findAllFavorite();

    Object[] findFavoriteByRecipeSeqAndEmail(Long recipeSeq, String email);
}
