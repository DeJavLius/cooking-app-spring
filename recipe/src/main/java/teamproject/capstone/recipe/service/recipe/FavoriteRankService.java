package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.Favorite;

import java.util.List;

public interface FavoriteRankService {
    List<Favorite> mostFavoriteRecipe();
    List<Favorite> usersFavoriteRecipe(String email);
    List<Long> usersFavoriteOnlySeq(String email);
}
