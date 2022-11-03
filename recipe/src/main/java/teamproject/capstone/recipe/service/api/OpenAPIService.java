package teamproject.capstone.recipe.service.api;

import teamproject.capstone.recipe.domain.api.OpenRecipe;

import java.util.List;

public interface OpenAPIService {
    OpenRecipe create(OpenRecipe openRecipe);
    List<OpenRecipe> createAll(List<OpenRecipe> openRecipes);
}
