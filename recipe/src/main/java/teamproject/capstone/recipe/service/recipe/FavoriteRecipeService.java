package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;

import java.util.List;

public interface FavoriteRecipeService {
    FavoriteRecipe create(FavoriteRecipe favoriteRecipe);
    List<FavoriteRecipe> createAll(List<FavoriteRecipe> favoriteRecipes);
    void delete(FavoriteRecipe favoriteRecipe);
    void deleteByEmail(String email);
    void deleteAll(List<FavoriteRecipe> favoriteRecipes);
}
