package teamproject.capstone.recipe.service.recipe;

import java.util.List;

public interface FavoriteRankService {
    List<Long> mostFavoriteRankRecipe();
    List<Long> allFavoriteRecipe(String email);
}
