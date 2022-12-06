package teamproject.capstone.recipe.utils.api.json;

import lombok.*;
import teamproject.capstone.recipe.domain.recipe.Favorite;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FavoriteData {
    private int count;
    private List<Favorite> favoriteRecipes;
}
