package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;

import java.util.List;

public interface FavoriteService {
    Favorite create(Favorite favoriteRecipe);
    List<Favorite> createAll(List<Favorite> favoriteRecipes);
    void delete(String email, Long recipeSeq);
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
                .recipeSeq(favoriteEntity.getRecipe().getRcpSeq())
                .userEmail(favoriteEntity.getUserEmail())
                .build();
    }

    default FavoriteEntity dtoToEntity(Favorite favorite) {
        OpenRecipeEntity recipe = OpenRecipeEntity.builder().id(favorite.getId()).rcpSeq(favorite.getRecipeSeq()).build();

        return FavoriteEntity.builder()
                .id(favorite.getId())
                .recipe(recipe)
                .userEmail(favorite.getUserEmail())
                .build();
    }
}
