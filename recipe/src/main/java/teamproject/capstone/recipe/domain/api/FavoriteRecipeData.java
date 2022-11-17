package teamproject.capstone.recipe.domain.api;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavoriteRecipeData {
    private Long recipe_seq;
    private String user_email;
}
