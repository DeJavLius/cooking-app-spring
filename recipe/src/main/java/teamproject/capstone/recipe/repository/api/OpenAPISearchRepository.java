package teamproject.capstone.recipe.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.APISearch;

public interface OpenAPISearchRepository {
    Page<OpenRecipeEntity> openAPISearchPageHandling(APISearch search, Pageable pageable);
}
