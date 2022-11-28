package teamproject.capstone.recipe.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.page.Search;

import java.util.List;

public interface OpenRecipePageWithSearchRepository {
    Page<OpenRecipeEntity> openAPIPageHandling(Pageable pageable);
    Page<OpenRecipeEntity> openAPISearchOrPageHandling(List<Search> searchKeywords, Pageable pageable);
    Page<OpenRecipeEntity> openAPISearchAndPageHandling(List<Search> searchKeywords, Pageable pageable);

    Page<OpenRecipeEntity> recipeSearchAndPageHandling();
}
