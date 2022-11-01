package teamproject.capstone.recipe.util.api;

import lombok.extern.slf4j.Slf4j;
import teamproject.capstone.recipe.domain.api.CookRecipe;
import teamproject.capstone.recipe.util.api.values.APICode;
import teamproject.capstone.recipe.util.api.values.ConstValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class APIProvider {
    private final APIManager apiManager = new APIManager();
    private final APIParser apiParser = new APIParser();
    private final APIError apiError = new APIError();

    private int startIndex = 1;
    private int endIndex = 0;

    public void lastOpenAPIDataUpdate() {

    }

    public List<CookRecipe> requestAllOpenAPI() {
        List<CookRecipe> cookRecipes = new ArrayList<>();

        defaultIndex();
        while (true) {
            apiManager.urlAvailability(startIndex, endIndex);

            try {
                CookRecipe needValueCheck = requestOpenAPIFromURL();
                CookRecipe checkedData = apiError.cookRecipeRightValueCheck(needValueCheck);
                cookRecipes.add(checkedData);
            } catch (Exception e) {
                e.getStackTrace();
            }

            valueIncrease();
        }
    }

    private CookRecipe requestOpenAPIFromURL() {
        try {
            return apiParser.parseStringToCookRecipe();
        } catch (IOException e) {
            e.getStackTrace();
            log.error("wrong json value : can't parse Json to String need to check");
            throw new IllegalArgumentException();
        }
    }

    private void defaultIndex() {
        this.endIndex = ConstValues.MAXIMUM_REQUEST;
    }

    private void valueIncrease() {
        startIndex += ConstValues.MAXIMUM_REQUEST;
        endIndex += ConstValues.MAXIMUM_REQUEST;
    }
}
