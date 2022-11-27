package teamproject.capstone.recipe.utils.api.openApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import teamproject.capstone.recipe.utils.api.json.OpenAPIRecipe;
import teamproject.capstone.recipe.utils.errors.OpenAPIErrorHandler;

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
    private final OpenAPIErrorHandler openApiErrorHandler = new OpenAPIErrorHandler();

    private static final int MAXIMUM_REQUEST = 1000;

    private int totalIndex = 0;

    public List<OpenAPIRecipe> requestAllOpenAPI() {
        List<OpenAPIRecipe> openAPIRecipes = new ArrayList<>();
        List<OpenAPIRecipe> resultOfFetchValues = startScanOpenAPI(openAPIRecipes);
        return resultOfFetchValues;
    }

    private List<OpenAPIRecipe> startScanOpenAPI(List<OpenAPIRecipe> openAPIRecipes) {
        int endSearch = MAXIMUM_REQUEST;
        setTotalIndex();
        for (int search = 1; search < totalIndex; search += MAXIMUM_REQUEST) {
            openApiProvider.urlIndexRangeScan(search, endSearch);
            OpenAPIRecipe requestCR = cookRecipeRequest(openApiProvider.getApi().getAPIUrl());

            openAPIRecipes.add(requestCR);

            endSearch += MAXIMUM_REQUEST;
        }
        return openAPIRecipes;
    }

    private void setTotalIndex() {
        int searchTotalNumber = 1;
        openApiProvider.urlIndexRangeScan(searchTotalNumber, searchTotalNumber);
        OpenAPIRecipe totalSearch = cookRecipeRequest(openApiProvider.getApi().getAPIUrl());
        this.totalIndex = Integer.parseInt(totalSearch.getTotalCount());
    }

    private OpenAPIRecipe cookRecipeRequest(URL apiUrl) {
        try {
            OpenAPIRecipe needValueCheck = requestOpenAPIFromURL(apiUrl);
            return openApiErrorHandler.cookRecipeRightValueCheck(needValueCheck);
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
}
