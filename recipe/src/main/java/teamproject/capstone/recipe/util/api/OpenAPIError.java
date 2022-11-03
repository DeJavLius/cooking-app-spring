package teamproject.capstone.recipe.util.api;

import lombok.extern.slf4j.Slf4j;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.util.api.values.OpenAPICode;

@Slf4j
public class OpenAPIError {
    public OpenRecipe cookRecipeRightValueCheck(OpenRecipe openRecipe) {
        if (openRecipe.getResult().getMsg().equals(OpenAPICode.ERROR_334.getCode())) {
            log.error("wrong index position : start index is bigger then end index");
            throw new IllegalArgumentException();
        } else if (openRecipe.getResult().getMsg().equals(OpenAPICode.INFO_200.getCode())) {
            log.error("no more data found : API data is no more");
            throw new IllegalArgumentException();
        } else {
            return cookRecipeNullCheck(openRecipe);
        }
    }

    private OpenRecipe cookRecipeNullCheck(OpenRecipe openRecipe) {
    if (openRecipe.getResult().getMsg().isEmpty()) {
            log.error("no data found : object has no value error");
            throw new NullPointerException();
        } else {
            return openRecipe;
        }
    }

    public OpenRecipe cookRecipeValueCheck(OpenRecipe openRecipe) {
        if (openRecipe.getRow().isEmpty()) {
            log.error("empty value requested : value is lost");
            throw new NullPointerException();
        } else {
            return openRecipe;
        }
    }

    public OpenRecipe cookRecipeInnerValueCheck(OpenRecipe openRecipe) {
        if (openRecipe.getRow().get(0).getRcpSeq() == null) {
            log.error("empty value requested : inside Row value is lost");
            throw new NullPointerException();
        } else {
            return openRecipe;
        }
    }
}
