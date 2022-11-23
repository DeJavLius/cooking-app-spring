package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;
import teamproject.capstone.recipe.entity.recipe.FavoriteRecipeEntity;

import java.util.List;

public interface FavoriteRecipeService {
    FavoriteRecipe create(FavoriteRecipe favoriteRecipe);
    List<FavoriteRecipe> createAll(List<FavoriteRecipe> favoriteRecipes);
    void delete(FavoriteRecipe favoriteRecipe);
    void deleteByEmail(String email);
    void deleteAll();

    FavoriteRecipe findRecipe(long recipeSeq, String email);
    List<FavoriteRecipe> findAll();
    List<FavoriteRecipe> findByEmail(String email);
    List<FavoriteRecipe> findBySeq(long recipeSeq);

    default FavoriteRecipe entityToDto(FavoriteRecipeEntity favoriteRecipeEntity) {
        return FavoriteRecipe.builder()
                .id(favoriteRecipeEntity.getId())
                .recipeSeq(favoriteRecipeEntity.getRecipeSeq())
                .userEmail(favoriteRecipeEntity.getUserEmail())
                .build();
    }

    default FavoriteRecipeEntity dtoToEntity(FavoriteRecipe favoriteRecipe) {
        return FavoriteRecipeEntity.builder()
                .id(favoriteRecipe.getId())
                .recipeSeq(favoriteRecipe.getRecipeSeq())
                .userEmail(favoriteRecipe.getUserEmail())
                .build();
    }
}
