package teamproject.capstone.recipe.service.api;

import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.page.PageResult;
import teamproject.capstone.recipe.utils.page.Search;

import java.util.List;

public interface OpenAPISearchService {
    PageResult<OpenRecipe, OpenRecipeEntity> searchOrAPIDataSources(List<Search> searchList, PageRequest pageRequest);
    PageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(List<Search> searchList, PageRequest pageRequest);
}
