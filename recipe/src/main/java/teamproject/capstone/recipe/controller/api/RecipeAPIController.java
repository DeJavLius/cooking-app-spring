package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.domain.recipe.Part;
import teamproject.capstone.recipe.domain.recipe.Way;
import teamproject.capstone.recipe.service.recipe.FavoriteService;
import teamproject.capstone.recipe.service.recipe.OpenRecipeService;
import teamproject.capstone.recipe.service.recipe.RecipeService;
import teamproject.capstone.recipe.utils.api.json.FavoriteData;
import teamproject.capstone.recipe.utils.api.json.Sequences;
import teamproject.capstone.recipe.utils.api.json.parts.FavoriteRecipe;
import teamproject.capstone.recipe.utils.firebase.FirebaseUserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/favorites")
@RestController
@RequiredArgsConstructor
@Slf4j
public class RecipeAPIController {
    private final FavoriteService favoriteService;
    private final FirebaseUserManager firebaseUserManager;
    private final RecipeService recipeService;
    private final OpenRecipeService openRecipeService;

    @GetMapping("/give")
    public FavoriteData requestAllFavoriteRecipe() {
        List<Favorite> findValues = favoriteService.findAll();
        List<FavoriteRecipe> favoriteRecipes = favoriteRecipeSerialization(findValues);

        return FavoriteData.builder()
                .count(findValues.size())
                .favoriteRecipes(favoriteRecipes)
                .build();
    }

    @GetMapping("/give/user")
    public FavoriteData requestUsersFavoriteRecipe(@RequestParam String uid) {
        List<Favorite> findValues = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            findValues = favoriteService.findByEmail(email);
        }

        List<FavoriteRecipe> favoriteRecipes = favoriteRecipeSerialization(findValues);

        return FavoriteData.builder()
                .count(findValues.size())
                .favoriteRecipes(favoriteRecipes)
                .build();
    }

    @GetMapping("/give/recipe")
    public FavoriteData requestByRecipeSeqFavoriteRecipe(@RequestParam Long recipeSeq) {
        List<Favorite> findValues = favoriteService.findBySeq(recipeSeq);

        List<FavoriteRecipe> favoriteRecipes = favoriteRecipeSerialization(findValues);

        return FavoriteData.builder()
                .count(findValues.size())
                .favoriteRecipes(favoriteRecipes)
                .build();
    }

    @GetMapping("/give/favorite")
    public boolean isFavoriteRecipe(@RequestParam String email, @RequestParam Long recipeSeq) {
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
            List<OpenRecipe> openRecipe = openRecipeService.findByRecipeSeqList(sequences.getSequences());

            savedValues = favoriteService.createAll(email, openRecipe);
        }

        return FavoriteData.builder()
                .count(savedValues.size())
                .favoriteRecipes(favoriteRecipeSerialization(savedValues))
                .build();
    }

    @PostMapping("/take/choose")
    public FavoriteData responseOneFavoriteRecipe(@RequestParam String uid, @RequestParam Long recipeSeq) {
        List<Favorite> savedValues = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            OpenRecipe recipe = openRecipeService.findByRecipeSeq(recipeSeq);
            Favorite favorite = favoriteService.findRecipe(recipeSeq, email);

            if (favorite.getId().equals(0L)) {
                Favorite build = Favorite.builder()
                        .recipeId(recipe.getId())
                        .recipeSeq(recipe.getRcpSeq())
                        .userEmail(email)
                        .build();

                favorite = favoriteService.create(build);
            }
            savedValues.add(favorite);
        }

        return FavoriteData.builder()
                .count(savedValues.size())
                .favoriteRecipes(favoriteRecipeSerialization(savedValues))
                .build();
    }

    @PostMapping("/delete")
    public FavoriteData deleteAllFavoriteRecipe() {
        List<Favorite> deleteCheck = new ArrayList<>();

        favoriteService.deleteAll();
        deleteCheck = favoriteService.findAll();

        return FavoriteData.builder()
                .count(deleteCheck.size())
                .favoriteRecipes(favoriteRecipeSerialization(deleteCheck))
                .build();
    }

    @PostMapping("/delete/user")
    public FavoriteData deleteAllByEmailFavoriteRecipe(@RequestParam String uid) {
        List<Favorite> deleteCheck = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            favoriteService.deleteByEmail(email);

            deleteCheck = favoriteService.findByEmail(email);
        }
        return FavoriteData.builder()
                .count(deleteCheck.size())
                .favoriteRecipes(favoriteRecipeSerialization(deleteCheck))
                .build();
    }

    @PostMapping("/delete/choose")
    public FavoriteData deleteFavoriteRecipe(@RequestParam String uid, @RequestParam Long recipeSeq) {
        List<Favorite> deleteCheck = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            Favorite recipe = favoriteService.findRecipe(recipeSeq, email);
            favoriteService.delete(recipe);

            deleteCheck = favoriteService.findByEmail(email);
        }

        return FavoriteData.builder()
                .count(deleteCheck.size())
                .favoriteRecipes(favoriteRecipeSerialization(deleteCheck))
                .build();
    }

    @GetMapping("/attributes/tuple/way")
    public List<String> tupleWayValueFound() {
        return recipeService.recipeWayValueFound().stream().map(Way::getValue).collect(Collectors.toList());
    }

    @GetMapping("/attributes/tuple/part")
    public List<String> tuplePartValueFound() {
        return recipeService.recipePartValueFound().stream().map(Part::getValue).collect(Collectors.toList());
    }

    private List<FavoriteRecipe> favoriteRecipeSerialization(List<Favorite> values) {
        return values.stream().map(this::serialization).collect(Collectors.toList());
    }

    private FavoriteRecipe serialization(Favorite favorite) {
        return FavoriteRecipe.builder()
                .id(favorite.getId())
                .recipeId(favorite.getRecipeId())
                .recipeSeq(favorite.getRecipeSeq())
                .userEmail(favorite.getUserEmail())
                .build();
    }
}
