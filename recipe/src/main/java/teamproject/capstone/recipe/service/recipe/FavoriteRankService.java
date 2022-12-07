package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.Favorite;

import java.util.List;

public interface FavoriteRankService {
    List<Favorite> usersFavoriteRecipe(String email);
}
