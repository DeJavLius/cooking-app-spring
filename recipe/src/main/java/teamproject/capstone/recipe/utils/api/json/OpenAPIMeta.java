package teamproject.capstone.recipe.utils.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenAPIMeta {
    @JsonProperty("COOKRCP01")
    OpenAPIRecipe openAPIRecipe;

    public OpenAPIRecipe getOpenRecipe() {
        return this.openAPIRecipe;
    }
}
