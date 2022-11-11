package teamproject.capstone.recipe.service.api;

import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.APIPageResult;

public interface OpenAPIPageService {
    APIPageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest);
}
