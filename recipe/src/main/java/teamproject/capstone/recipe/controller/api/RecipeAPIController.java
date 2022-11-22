package teamproject.capstone.recipe.controller.api;

import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;
import teamproject.capstone.recipe.service.recipe.FavoriteRecipeService;
import teamproject.capstone.recipe.utils.firebase.FirebaseUserManager;

import java.util.List;

@RequestMapping("api/recipes/")
@RequiredArgsConstructor
@RestController
public class RecipeAPIController {
    private final FavoriteRecipeService favoriteRecipeService;
    private final FirebaseUserManager firebaseUserManager;

    @GetMapping("v1/favorites/give/all")
    public List<FavoriteRecipe> requestAllFavoriteRecipe() {
        return favoriteRecipeService.findAll();
    }

    @GetMapping("v1/favorites/give/choose")
    public List<FavoriteRecipe> requestUsersAllFavoriteRecipe(@RequestParam String uid) {
//        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            List<FavoriteRecipe> byEmail = favoriteRecipeService.findByEmail(email);
            return byEmail;
//        }
    }

    @PostMapping("v1/favorites/take/all")
    public FavoriteRecipe responseAllFavoriteRecipe(@RequestParam String uid, @RequestBody List<FavoriteRecipe> favoriteData) {
        return new FavoriteRecipe();
    }

    @PostMapping("v1/favorites/take/choose")
    public FavoriteRecipe responseOneFavoriteRecipe(@RequestParam String uid, @RequestBody FavoriteRecipe favoriteData) {
        return new FavoriteRecipe();
    }

    @PostMapping("v1/favorites/delete/all")
    public FavoriteRecipe deleteAllFavoriteRecipe(@RequestParam String uid) {
        return new FavoriteRecipe();
    }

    @PostMapping("v1/favorites/delete/choose")
    public FavoriteRecipe deleteFavoriteRecipe(@RequestParam String uid, @RequestBody FavoriteRecipe favoriteData) {
        return new FavoriteRecipe();
    }
}
