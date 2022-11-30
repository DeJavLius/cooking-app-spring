package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;

import java.util.List;

public interface OpenRecipeFavoriteService {
    List<FavoriteRecipe> provideFavorites(String email, List<Long> recipeSeqList);
    FavoriteRecipe provideFavorite(String email, Long recipeSeq);
    List<OpenRecipe> rankFavoriteRecipe(List<Long> favoriteSequences);
}
