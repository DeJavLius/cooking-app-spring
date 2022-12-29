package teamproject.capstone.recipe.service.recipe;

import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.domain.recipe.Part;
import teamproject.capstone.recipe.domain.recipe.Way;
import teamproject.capstone.recipe.domain.recipe.manual.RecipeManual;
import teamproject.capstone.recipe.domain.recipe.manual.RecipeManualImg;

import java.util.ArrayList;
import java.util.List;

public interface RecipeService {
    default List<RecipeManual> recipeManualSplit(OpenRecipe openRecipe) {
        List<RecipeManual> recipeManuals = new ArrayList<>();

        if (!openRecipe.getManual01().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual01()));
        }
        if (!openRecipe.getManual02().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual02()));
        }
        if (!openRecipe.getManual03().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual03()));
        }
        if (!openRecipe.getManual04().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual04()));
        }
        if (!openRecipe.getManual05().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual05()));
        }
        if (!openRecipe.getManual06().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual06()));
        }
        if (!openRecipe.getManual07().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual07()));
        }
        if (!openRecipe.getManual08().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual08()));
        }
        if (!openRecipe.getManual09().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual09()));
        }
        if (!openRecipe.getManual10().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual10()));
        }
        if (!openRecipe.getManual11().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual11()));
        }
        if (!openRecipe.getManual12().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual12()));
        }
        if (!openRecipe.getManual13().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual13()));
        }
        if (!openRecipe.getManual14().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual14()));
        }
        if (!openRecipe.getManual15().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual15()));
        }
        if (!openRecipe.getManual16().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual16()));
        }
        if (!openRecipe.getManual17().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual17()));
        }
        if (!openRecipe.getManual18().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual18()));
        }
        if (!openRecipe.getManual19().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual19()));
        }
        if (!openRecipe.getManual20().isEmpty()) {
            recipeManuals.add(new RecipeManual(openRecipe.getManual20()));
        }

        return recipeManuals;
    }

    default List<RecipeManualImg> recipeManualImgSplit(OpenRecipe openRecipe) {
        List<RecipeManualImg> recipeManualImages = new ArrayList<>();

        if (!openRecipe.getManualImg01().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg01()));
        }
        if (!openRecipe.getManualImg02().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg02()));
        }
        if (!openRecipe.getManualImg03().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg03()));
        }
        if (!openRecipe.getManualImg04().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg04()));
        }
        if (!openRecipe.getManualImg05().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg05()));
        }
        if (!openRecipe.getManualImg06().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg06()));
        }
        if (!openRecipe.getManualImg07().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg07()));
        }
        if (!openRecipe.getManualImg08().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg08()));
        }
        if (!openRecipe.getManualImg09().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg09()));
        }
        if (!openRecipe.getManualImg10().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg10()));
        }
        if (!openRecipe.getManualImg11().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg11()));
        }
        if (!openRecipe.getManualImg12().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg12()));
        }
        if (!openRecipe.getManualImg13().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg13()));
        }
        if (!openRecipe.getManualImg14().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg14()));
        }
        if (!openRecipe.getManualImg15().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg15()));
        }
        if (!openRecipe.getManualImg16().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg16()));
        }
        if (!openRecipe.getManualImg17().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg17()));
        }
        if (!openRecipe.getManualImg18().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg18()));
        }
        if (!openRecipe.getManualImg19().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg19()));
        }
        if (!openRecipe.getManualImg20().isEmpty()) {
            recipeManualImages.add(new RecipeManualImg(openRecipe.getManualImg20()));
        }

        return recipeManualImages;
    }

    List<Way> recipeWayValueFound();
    List<Part> recipePartValueFound();
}
