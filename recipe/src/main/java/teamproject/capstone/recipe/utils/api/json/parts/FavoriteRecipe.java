package teamproject.capstone.recipe.utils.api.json.parts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class FavoriteRecipe {
    private Long id;
    private Long recipeId;
    @JsonProperty("recipe_seq")
    private Long recipeSeq;
    @JsonProperty("user_email")
    private String userEmail;
}
