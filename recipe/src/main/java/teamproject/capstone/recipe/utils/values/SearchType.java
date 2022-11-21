package teamproject.capstone.recipe.utils.values;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {
    RECIPE_NAME("name"),
    RECIPE_DETAILS("detail"),
    RECIPE_PARTS("part"),
    RECIPE_WAY("way"),
    RECIPE_SEQUENCE("seq");

    private final String value;
}
