package teamproject.capstone.recipe.domain.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavoriteRecipe {
    private Long id;
    @JsonProperty("recipe_seq")
    private Long recipeSeq;
    @JsonProperty("user_email")
    private String userEmail;
}
