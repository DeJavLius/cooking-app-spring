package teamproject.capstone.recipe.utils.api.json;

import lombok.*;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FavoriteData {
    private int count;
    private List<FavoriteRecipe> favoriteRecipes;
}
