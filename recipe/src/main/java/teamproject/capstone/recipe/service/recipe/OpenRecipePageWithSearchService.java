package teamproject.capstone.recipe.service.recipe;

import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.page.RecipePageResult;
import teamproject.capstone.recipe.utils.page.Search;

import java.util.List;

public interface OpenRecipePageWithSearchService {
    APIPageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest);

    APIPageResult<OpenRecipe, OpenRecipeEntity> searchOrAPIDataSources(List<Search> searchList, PageRequest pageRequest);
    APIPageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(List<Search> searchList, PageRequest pageRequest);

    RecipePageResult<OpenRecipe, OpenRecipeEntity> searchPageWithSortRecipes(List<Search> searchList, PageRequest pageRequest);
}
