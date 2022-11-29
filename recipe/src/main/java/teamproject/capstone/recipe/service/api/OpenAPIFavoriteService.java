package teamproject.capstone.recipe.service.api;

import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;

import java.util.List;

public interface OpenAPIFavoriteService {
    List<FavoriteRecipe> provideFavorites(String email, List<Long> recipeSeqList);
    FavoriteRecipe provideFavorite(String email, Long recipeSeq);
    List<OpenRecipe> rankFavoriteRecipe(List<Long> favoriteSequences);
}
