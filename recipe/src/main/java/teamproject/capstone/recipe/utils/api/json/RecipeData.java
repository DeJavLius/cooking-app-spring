package teamproject.capstone.recipe.utils.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RecipeData {
    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("values")
    private List<OpenRecipe> openRecipes;
}
