package teamproject.capstone.recipe.util.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import teamproject.capstone.recipe.domain.api.OpenRecipe;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class OpenAPIHandler {
    private final OpenAPIProvider openApiProvider = OpenAPIProvider.getInstance();
    private final OpenAPIParser apiParser = OpenAPIParser.getInstance();
    private final OpenAPIError openApiError = new OpenAPIError();

    private static final int MAXIMUM_REQUEST = 1000;

    private int startIndex = 1;
    private int endIndex = 0;
    private int totalIndex = 0;

    public List<OpenRecipe> requestAllOpenAPI() {
        List<OpenRecipe> openRecipes = new ArrayList<>();

        return takeAllCookRecipes(openRecipes);
    }

    private List<OpenRecipe> takeAllCookRecipes(List<OpenRecipe> openRecipes) {
        defaultIndex();
        while (true) {
            openApiProvider.urlIndexRangeScan(startIndex, endIndex);
            OpenRecipe requestCR = cookRecipeRequest(openApiProvider.getApi().getAPIUrl());

            setTotalIndex(Integer.parseInt(requestCR.getTotalCount()));

            if (startIndex > totalIndex) {
                break;
            } else {
                openRecipes.add(requestCR);
            }

            indexValueIncrease();
        }

        return openRecipes;
    }

    private void defaultIndex() {
        this.endIndex = MAXIMUM_REQUEST;
    }

    private void setTotalIndex(int totalIndex) {
        this.totalIndex = totalIndex;
    }

    private OpenRecipe cookRecipeRequest(URL apiUrl) {
        try {
            OpenRecipe needValueCheck = requestOpenAPIFromURL(apiUrl);
            return openApiError.cookRecipeRightValueCheck(needValueCheck);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    private OpenRecipe requestOpenAPIFromURL(URL apiUrl) {
        try {
            return apiParser.parseURLToCookRecipe(apiUrl);
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
