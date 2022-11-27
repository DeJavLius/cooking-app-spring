package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.api.OpenRecipe;

import java.util.List;

public interface FavoriteRecipeRankService {
    List<Long> mostFavoriteRankRecipe();
}
