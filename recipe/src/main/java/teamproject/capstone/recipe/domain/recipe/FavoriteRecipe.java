package teamproject.capstone.recipe.domain.recipe;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavoriteRecipe {
    private Long id;
    private Long recipe_id;
    private String user_email;
}
