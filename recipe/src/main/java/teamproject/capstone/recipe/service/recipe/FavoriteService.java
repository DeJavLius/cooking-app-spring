package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;

import java.util.List;
import java.util.function.Function;

public interface FavoriteService {
    Favorite create(Favorite favoriteRecipe);
    List<Favorite> createAll(List<Favorite> favoriteRecipes);
    void deleteByEmail(String email);
    void deleteAll();

    Favorite findRecipe(long recipeSeq, String email);
    List<Favorite> findAll();
    List<Favorite> findByEmail(String email);
    List<Favorite> findBySeq(long recipeSeq);

    boolean isFavoriteNotExist(Favorite favorite);

    default Favorite entityToDto(FavoriteEntity favoriteEntity) {
        return Favorite.builder()
                .id(favoriteEntity.getId())
                .recipeId(favoriteEntity.getRecipe().getId())
                .recipeSeq(favoriteEntity.getRecipe().getRcpSeq())
                .recipeMainImage(favoriteEntity.getRecipe().getAttFileNoMain())
                .recipePart(favoriteEntity.getRecipe().getRcpPat2())
                .recipeWay(favoriteEntity.getRecipe().getRcpWay2())
                .userEmail(favoriteEntity.getUserEmail())
                .build();
    }

    default Function<Object[], Favorite> objectEntityToDto() {
        return (entity -> recipeWithEntityToDto((FavoriteEntity) entity[0], (OpenRecipeEntity) entity[1]));
    }

    default Favorite recipeWithEntityToDto(FavoriteEntity favoriteEntity, OpenRecipeEntity openRecipeEntity) {
        return Favorite.builder()
                .id(favoriteEntity.getId())
                .recipeId(openRecipeEntity.getId())
                .recipeSeq(openRecipeEntity.getRcpSeq())
                .recipeMainImage(openRecipeEntity.getAttFileNoMain())
                .recipePart(openRecipeEntity.getRcpPat2())
                .recipeWay(openRecipeEntity.getRcpWay2())
                .userEmail(favoriteEntity.getUserEmail())
                .build();
    }

    default FavoriteEntity dtoToEntity(Favorite favorite) {
        OpenRecipeEntity recipe = OpenRecipeEntity.builder()
                .id(favorite.getRecipeId())
                .rcpSeq(favorite.getRecipeSeq())
                .attFileNoMain(favorite.getRecipeMainImage())
                .rcpWay2(favorite.getRecipeWay())
                .rcpPat2(favorite.getRecipePart())
                .build();

        return FavoriteEntity.builder()
                .id(favorite.getId())
                .recipe(recipe)
                .userEmail(favorite.getUserEmail())
                .build();
    }
}
