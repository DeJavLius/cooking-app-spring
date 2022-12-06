package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.service.recipe.OpenRecipeFavoriteService;
import teamproject.capstone.recipe.service.recipe.FavoriteService;
import teamproject.capstone.recipe.service.recipe.RecipeService;
import teamproject.capstone.recipe.utils.api.json.FavoriteData;
import teamproject.capstone.recipe.utils.api.json.Sequences;
import teamproject.capstone.recipe.utils.firebase.FirebaseUserManager;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/favorites")
@RestController
@RequiredArgsConstructor
@Slf4j
public class RecipeAPIController {
    private final FavoriteService favoriteService;
    private final FirebaseUserManager firebaseUserManager;
    private final OpenRecipeFavoriteService openRecipeFavoriteService;
    private final RecipeService recipeService;

    @GetMapping("/give")
    public FavoriteData requestAllFavoriteRecipe() {
        List<Favorite> findValues = favoriteService.findAll();

        return FavoriteData.builder()
                .count(findValues.size())
                .favoriteRecipes(findValues)
                .build();
    }

    @GetMapping("/give/user")
    public FavoriteData requestUsersFavoriteRecipe(@RequestParam String uid) {
        List<Favorite> findValues = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            findValues = favoriteService.findByEmail(email);
        }

        return FavoriteData.builder()
                .count(findValues.size())
                .favoriteRecipes(findValues)
                .build();
    }

    @GetMapping("/give/recipe")
    public FavoriteData requestByRecipeSeqFavoriteRecipe(@RequestParam int recipeSeq) {
        List<Favorite> findValues = favoriteService.findBySeq(recipeSeq);

        return FavoriteData.builder()
                .count(findValues.size())
                .favoriteRecipes(findValues)
                .build();
    }

    @GetMapping("/give/favorite")
    public boolean isFavoriteRecipe(@RequestParam String email, @RequestParam int recipeSeq) {
        Favorite recipe = favoriteService.findRecipe(recipeSeq, email);
        return isFavoriteCheck(recipe);
    }

    private boolean isFavoriteCheck(Favorite fRecipe) {
        return fRecipe.getRecipeSeq() != null;
    }

    @PostMapping("/take")
    public FavoriteData responseAllFavoriteRecipe(@RequestParam String uid, @RequestBody Sequences sequences) {
        List<Favorite> savedValues = new ArrayList<>();
        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            List<Favorite> result = openRecipeFavoriteService.provideFavorites(email, sequences.getSequences());

            savedValues = favoriteService.createAll(result);
        }

        return FavoriteData.builder()
                .count(savedValues.size())
                .favoriteRecipes(savedValues)
                .build();
    }

    @PostMapping("/take/choose")
    public FavoriteData responseOneFavoriteRecipe(@RequestParam String uid, @RequestParam Long recipeSeq) {
        List<Favorite> savedValues = new ArrayList<>();
        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            Favorite result = openRecipeFavoriteService.provideFavorite(email, recipeSeq);

            if (favoriteService.isFavoriteNotExist(result)) {
                Favorite favoriteRecipe = favoriteService.create(result);

                savedValues.add(favoriteRecipe);
            }
        }

        return FavoriteData.builder()
                .count(savedValues.size())
                .favoriteRecipes(savedValues)
                .build();
    }

    @PostMapping("/delete")
    public FavoriteData deleteAllFavoriteRecipe(@RequestParam String uid) {
        List<Favorite> deleteCheck = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            favoriteService.deleteByEmail(email);

            deleteCheck = favoriteService.findByEmail(email);
        }
        return FavoriteData.builder()
                .count(deleteCheck.size())
                .favoriteRecipes(deleteCheck)
                .build();
    }

    @PostMapping("/delete/choose")
    public FavoriteData deleteFavoriteRecipe(@RequestParam String uid, @RequestParam Long recipeSeq) {
        List<Favorite> deleteCheck = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            favoriteService.delete(email, recipeSeq);

            deleteCheck = favoriteService.findByEmail(email);
        }

        return FavoriteData.builder()
                .count(deleteCheck.size())
                .favoriteRecipes(deleteCheck)
                .build();
    }

    @GetMapping("/attributes/tuple/way")
    public List<String> tupleWayValueFound() {
        return recipeService.recipeWayValueFound();
    }

    @GetMapping("/attributes/tuple/part")
    public List<String> tuplePartValueFound() {
        return recipeService.recipePartValueFound();
    }
}
