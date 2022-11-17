package teamproject.capstone.recipe.controller.api;

import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.api.FavoriteRecipeData;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;

import java.util.List;

@RequestMapping("api/recipes/")
@RestController
public class RecipeAPIController {

    @GetMapping("favorites/give/all")
    public FavoriteRecipe requestAllFavoriteRecipe() {
        return new FavoriteRecipe();
    }

    @GetMapping("favorites/give/choose")
    public FavoriteRecipe requestOneFavoriteRecipe(@RequestParam String user_email) {
        return new FavoriteRecipe();
    }

    @PostMapping("favorites/take/all")
    public FavoriteRecipeData responseAllFavoriteRecipe(@RequestBody List<FavoriteRecipeData> favoriteData) {
        return new FavoriteRecipeData();
    }

    @PostMapping("favorites/take/choose")
    public FavoriteRecipeData responseOneFavoriteRecipe(@RequestBody FavoriteRecipeData favoriteData) {
        return new FavoriteRecipeData();
    }
}
