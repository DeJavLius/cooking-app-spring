package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;
import teamproject.capstone.recipe.domain.recipe.Recommend;
import teamproject.capstone.recipe.utils.page.Search;

import java.util.List;

public interface RecipeTupleRepository {
    List<String> recipeWayExtract();
    List<String> recipePartExtract();
    List<Object[]> sameRecommendRecipe(Search search);
}
