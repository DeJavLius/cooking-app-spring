package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.domain.recipe.Recommend;
import teamproject.capstone.recipe.utils.page.Search;

import java.util.List;

public interface OpenRecipeService {
    OpenRecipe create(OpenRecipe openRecipe);
    List<OpenRecipe> createAll(List<OpenRecipe> openRecipes);

    void delete(OpenRecipe openRecipe);
    void deleteAll();

    OpenRecipe findRecipe(Long id);
    OpenRecipe findByRecipeSeq(Long recipeSeq);
    List<OpenRecipe> findByRecipeSeqList(List<Long> seqList);
}