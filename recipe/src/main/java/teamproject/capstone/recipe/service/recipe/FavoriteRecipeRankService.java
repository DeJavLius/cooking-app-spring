package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;

import java.util.List;

public interface FavoriteRecipeRankService {
    List<Long> mostFavoriteRankRecipe();
}
