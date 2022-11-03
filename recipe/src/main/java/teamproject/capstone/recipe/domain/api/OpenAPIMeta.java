package teamproject.capstone.recipe.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenAPIMeta {
    @JsonProperty("COOKRCP01")
    OpenRecipe openRecipe;

    public OpenRecipe getOpenRecipe() {
        return this.openRecipe;
    }
}
