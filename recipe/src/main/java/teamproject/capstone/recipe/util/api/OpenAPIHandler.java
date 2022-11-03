package teamproject.capstone.recipe.util.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import teamproject.capstone.recipe.domain.api.OpenAPIRecipe;

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

    public List<OpenAPIRecipe> requestAllOpenAPI() {
        List<OpenAPIRecipe> openAPIRecipes = new ArrayList<>();

        return takeAllCookRecipes(openAPIRecipes);
    }

    private List<OpenAPIRecipe> takeAllCookRecipes(List<OpenAPIRecipe> openAPIRecipes) {
        defaultIndex();
        while (true) {
            openApiProvider.urlIndexRangeScan(startIndex, endIndex);
            OpenAPIRecipe requestCR = cookRecipeRequest(openApiProvider.getApi().getAPIUrl());

            setTotalIndex(Integer.parseInt(requestCR.getTotalCount()));

            if (startIndex > totalIndex) {
                break;
            } else {
                openAPIRecipes.add(requestCR);
            }

            indexValueIncrease();
        }

        return openAPIRecipes;
    }

    private void defaultIndex() {
        this.endIndex = MAXIMUM_REQUEST;
    }

    private void setTotalIndex(int totalIndex) {
        this.totalIndex = totalIndex;
    }

    private OpenAPIRecipe cookRecipeRequest(URL apiUrl) {
        try {
            OpenAPIRecipe needValueCheck = requestOpenAPIFromURL(apiUrl);
            return openApiError.cookRecipeRightValueCheck(needValueCheck);
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    private OpenAPIRecipe requestOpenAPIFromURL(URL apiUrl) {
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
