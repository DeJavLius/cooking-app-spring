package teamproject.capstone.recipe.util.api;

import lombok.extern.slf4j.Slf4j;
import teamproject.capstone.recipe.domain.api.OpenRecipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OpenAPIProvider {
    private final OpenAPIManager openApiManager = new OpenAPIManager();
    private final OpenAPIParser apiParser = new OpenAPIParser();
    private final OpenAPIError openApiError = new OpenAPIError();
    private static final int MAXIMUM_REQUEST = 1000;

    private int startIndex = 1;
    private int endIndex = 0;

    public List<OpenRecipe> requestAllOpenAPI() {
        List<OpenRecipe> openRecipes = new ArrayList<>();

        return takeAllCookRecipes(openRecipes);
    }

    private List<OpenRecipe> takeAllCookRecipes(List<OpenRecipe> openRecipes) {
        defaultIndex();
        while (true) {
            openApiManager.urlIndexRangeScan(startIndex, endIndex);
            OpenRecipe requestCR = cookRecipeRequest();

            if (requestCR != null) {
                openRecipes.add(requestCR);
            } else {
                break;
            }

            indexValueIncrease();
        }

        return openRecipes;
    }

    private void defaultIndex() {
        this.endIndex = MAXIMUM_REQUEST;
    }

    private OpenRecipe cookRecipeRequest() {
        try {
            OpenRecipe needValueCheck = requestOpenAPIFromURL();
            log.info("test of requestOpenAPI : {}", needValueCheck);
            return openApiError.cookRecipeRightValueCheck(needValueCheck);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    private OpenRecipe requestOpenAPIFromURL() {
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
