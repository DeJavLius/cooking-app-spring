package teamproject.capstone.recipe.service.api;

import teamproject.capstone.recipe.domain.recipe.OpenRecipe;

import java.util.List;

public interface OpenAPIService {
    OpenRecipe create(OpenRecipe openRecipe);
    List<OpenRecipe> createAll(List<OpenRecipe> openRecipes);

    void delete(OpenRecipe openRecipe);
    void deleteAll();

    OpenRecipe findByRecipeSeq(Long recipeSeq);
}