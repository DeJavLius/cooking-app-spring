package teamproject.capstone.recipe.service.api;

import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.page.PageResult;

public interface OpenAPIPageService {
    PageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest);
}
