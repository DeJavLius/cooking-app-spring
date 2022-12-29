package teamproject.capstone.recipe.utils.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import teamproject.capstone.recipe.utils.api.json.parts.OpenAPIRecipe;

public class OpenAPIMeta {
    @JsonProperty("COOKRCP01")
    OpenAPIRecipe openAPIRecipe;

    public OpenAPIRecipe getOpenRecipe() {
        return this.openAPIRecipe;
    }
}
