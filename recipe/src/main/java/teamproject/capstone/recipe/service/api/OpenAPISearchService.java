package teamproject.capstone.recipe.service.api;

import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.api.APISearch;

import java.util.List;

public interface OpenAPISearchService {
    APIPageResult<OpenRecipe, OpenRecipeEntity> searchOrAPIDataSources(List<APISearch> apiSearchList, PageRequest pageRequest);
    APIPageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(List<APISearch> apiSearchList, PageRequest pageRequest);
}
