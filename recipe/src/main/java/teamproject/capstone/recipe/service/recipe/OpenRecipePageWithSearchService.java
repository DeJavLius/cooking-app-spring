package teamproject.capstone.recipe.service.recipe;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.page.RecipePageResult;
import teamproject.capstone.recipe.utils.page.Search;

import java.util.List;

public interface OpenRecipePageWithSearchService {
    APIPageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest);

    APIPageResult<OpenRecipe, OpenRecipeEntity> searchOrAPIDataSources(Search search, PageRequest pageRequest);
    APIPageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(Search search, PageRequest pageRequest);

    RecipePageResult<OpenRecipe, OpenRecipeEntity> searchPageWithSortRecipes(Search search, PageRequest pageRequest);
    RecipePageResult<Favorite, Object[]> searchTuplePageWithSortRecipes(Search search, PageRequest pageRequest);

    List<OpenRecipe> mostAndroidRecipe();
}
