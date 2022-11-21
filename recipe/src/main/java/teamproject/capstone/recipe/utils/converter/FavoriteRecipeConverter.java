package teamproject.capstone.recipe.utils.converter;

import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;
import teamproject.capstone.recipe.entity.recipe.FavoriteRecipeEntity;

public class FavoriteRecipeConverter {
    public static FavoriteRecipe entityToDto(FavoriteRecipeEntity favoriteRecipeEntity) {
        return FavoriteRecipe.builder()
                .id(favoriteRecipeEntity.getId())
                .recipe_id(favoriteRecipeEntity.getRecipe_id())
                .user_email(favoriteRecipeEntity.getUser_email())
                .build();
    }

    public static FavoriteRecipeEntity dtoToEntity(FavoriteRecipe favoriteRecipe) {
        return FavoriteRecipeEntity.builder()
                .id(favoriteRecipe.getId())
                .recipe_id(favoriteRecipe.getRecipe_id())
                .user_email(favoriteRecipe.getUser_email())
                .build();
    }
}
