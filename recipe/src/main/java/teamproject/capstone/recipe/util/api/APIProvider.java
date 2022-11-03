package teamproject.capstone.recipe.util.api;

import lombok.extern.slf4j.Slf4j;
import teamproject.capstone.recipe.domain.api.CookRecipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class APIProvider {
    private final APIManager apiManager = new APIManager();
    private final APIParser apiParser = new APIParser();
    private final APIError apiError = new APIError();
    private static final int MAXIMUM_REQUEST = 1000;

    private int startIndex = 1;
    private int endIndex = 0;

    public List<CookRecipe> requestAllOpenAPI() {
        List<CookRecipe> cookRecipes = new ArrayList<>();

        return takeAllCookRecipes(cookRecipes);
    }

    private List<CookRecipe> takeAllCookRecipes(List<CookRecipe> cookRecipes) {
        defaultIndex();
        while (true) {
            apiManager.urlIndexRangeScan(startIndex, endIndex);
            CookRecipe requestCR = cookRecipeRequest();

            if (requestCR != null) {
                cookRecipes.add(requestCR);
            } else {
                break;
            }

            indexValueIncrease();
        }

        return cookRecipes;
    }

    private void defaultIndex() {
        this.endIndex = MAXIMUM_REQUEST;
    }

    private CookRecipe cookRecipeRequest() {
        try {
            CookRecipe needValueCheck = requestOpenAPIFromURL();
            log.info("test of requestOpenAPI : {}", needValueCheck);
            return apiError.cookRecipeRightValueCheck(needValueCheck);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    private CookRecipe requestOpenAPIFromURL() {
        try {
            log.info("test of parseURLToCookRecipe : {}", apiParser.parseURLToCookRecipe());
            return apiParser.parseURLToCookRecipe();
        } catch (IOException e) {
            e.getStackTrace();
            log.error("wrong json value : can't parse Json to String need to check");
            throw new IllegalArgumentException();
        }
    }

    private void indexValueIncrease() {
        startIndex += MAXIMUM_REQUEST;
        endIndex += MAXIMUM_REQUEST;
    }
}
