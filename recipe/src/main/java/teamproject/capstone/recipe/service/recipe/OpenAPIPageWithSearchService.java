package teamproject.capstone.recipe.service.recipe;

import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.page.PageResult;
import teamproject.capstone.recipe.utils.page.Search;

import java.util.List;

public interface OpenAPIPageWithSearchService {
    PageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest);

    PageResult<OpenRecipe, OpenRecipeEntity> searchOrAPIDataSources(List<Search> searchList, PageRequest pageRequest);
    PageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(List<Search> searchList, PageRequest pageRequest);

    PageResult<OpenRecipe, OpenRecipeEntity> searchPageWithSortRecipes(List<Search> searchList, PageRequest pageRequest);
}
