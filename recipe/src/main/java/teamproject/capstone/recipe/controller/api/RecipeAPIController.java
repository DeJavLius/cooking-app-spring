package teamproject.capstone.recipe.controller.api;

import org.springframework.web.bind.annotation.*;
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
    public FavoriteRecipe requestUsersAllFavoriteRecipe(@RequestParam String user_email) {
        return new FavoriteRecipe();
    }

    @PostMapping("favorites/take/all")
    public FavoriteRecipe responseAllFavoriteRecipe(@RequestParam String user_email, @RequestBody List<FavoriteRecipe> favoriteData) {
        return new FavoriteRecipe();
    }

    @PostMapping("favorites/take/choose")
    public FavoriteRecipe responseOneFavoriteRecipe(@RequestParam String user_email, @RequestBody FavoriteRecipe favoriteData) {
        return new FavoriteRecipe();
    }

    @PostMapping("favorites/delete/all")
    public FavoriteRecipe deleteAllFavoriteRecipe(@RequestParam String user_email) {
        return new FavoriteRecipe();
    }

    @PostMapping("favorites/delete/choose")
    public FavoriteRecipe deleteFavoriteRecipe(@RequestParam String user_email, @RequestBody FavoriteRecipe favoriteData) {
        return new FavoriteRecipe();
    }
}
