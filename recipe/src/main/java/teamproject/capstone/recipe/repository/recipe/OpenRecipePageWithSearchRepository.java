package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.page.Search;

import java.util.List;

public interface OpenRecipePageWithSearchRepository {
    Page<OpenRecipeEntity> openAPIPageHandling(Pageable pageable);
    Page<OpenRecipeEntity> openAPISearchOrPageHandling(Search searchKeywords, Pageable pageable);
    Page<OpenRecipeEntity> openAPISearchAndPageHandling(Search searchKeywords, Pageable pageable);

    Page<OpenRecipeEntity> recipeSearchAndPageHandling(Search searchKeywords, Pageable pageable);
    Page<Object[]> recipeSearchAndPageSeparateHandling(Search searchKeywords, Pageable pageable);
}
