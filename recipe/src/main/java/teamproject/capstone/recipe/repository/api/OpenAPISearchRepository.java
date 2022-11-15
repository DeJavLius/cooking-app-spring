package teamproject.capstone.recipe.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.APISearch;

import java.util.List;

public interface OpenAPISearchRepository {
    Page<OpenRecipeEntity> openAPISearchOrPageHandling(List<APISearch> searchKeywords, Pageable pageable);
    Page<OpenRecipeEntity> openAPISearchAndPageHandling(List<APISearch> searchKeywords, Pageable pageable);
}
