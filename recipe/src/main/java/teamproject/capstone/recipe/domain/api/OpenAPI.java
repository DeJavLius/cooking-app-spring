package teamproject.capstone.recipe.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenAPI {
    @JsonProperty("COOKRCP01")
    CookRecipe cookRecipe;

    public CookRecipe getCookRecipe() {
        return this.cookRecipe;
    }
}
