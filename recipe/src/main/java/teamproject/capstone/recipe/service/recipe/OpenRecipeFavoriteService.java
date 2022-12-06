package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;

import java.util.List;

public interface OpenRecipeFavoriteService {
    List<Favorite> provideFavorites(String email, List<Long> recipeSeqList);
    Favorite provideFavorite(String email, Long recipeSeq);
    List<OpenRecipe> rankFavorite(List<Long> favoriteSequences);
}
