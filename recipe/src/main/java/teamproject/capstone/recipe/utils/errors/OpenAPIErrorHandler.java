package teamproject.capstone.recipe.utils.errors;

import lombok.extern.slf4j.Slf4j;
import teamproject.capstone.recipe.domain.api.OpenAPIRecipe;
import teamproject.capstone.recipe.utils.values.OpenAPICode;

@Slf4j
public class OpenAPIErrorHandler {
    public OpenAPIRecipe cookRecipeRightValueCheck(OpenAPIRecipe openAPIRecipe) {
        if (openAPIRecipe.getResult().getMsg().equals(OpenAPICode.ERROR_334.getCode())) {
            log.error("wrong index position : start index is bigger then end index");
            throw new IllegalArgumentException();
        } else if (openAPIRecipe.getResult().getMsg().equals(OpenAPICode.INFO_200.getCode())) {
            log.error("no more data found : API data is no more");
            throw new IllegalArgumentException();
        } else {
            return cookRecipeNullCheck(openAPIRecipe);
        }
    }

    private OpenAPIRecipe cookRecipeNullCheck(OpenAPIRecipe openAPIRecipe) {
    if (openAPIRecipe.getResult().getMsg().isEmpty()) {
            log.error("no data found : object has no value error");
            throw new NullPointerException();
        } else {
            return openAPIRecipe;
        }
    }

    public OpenAPIRecipe cookRecipeValueCheck(OpenAPIRecipe openAPIRecipe) {
        if (openAPIRecipe.getRow().isEmpty()) {
            log.error("empty value requested : value is lost");
            throw new NullPointerException();
        } else {
            return openAPIRecipe;
        }
    }

    public OpenAPIRecipe cookRecipeInnerValueCheck(OpenAPIRecipe openAPIRecipe) {
        if (openAPIRecipe.getRow().get(0).getRcpSeq() == null) {
            log.error("empty value requested : inside Row value is lost");
            throw new NullPointerException();
        } else {
            return openAPIRecipe;
        }
    }
}
