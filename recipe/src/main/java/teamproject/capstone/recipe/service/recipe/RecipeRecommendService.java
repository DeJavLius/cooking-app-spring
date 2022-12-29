package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.Recommend;
import teamproject.capstone.recipe.utils.page.Search;

import java.util.List;

public interface RecipeRecommendService {
    List<Recommend> findRecommendRecipe(Search search);
}
