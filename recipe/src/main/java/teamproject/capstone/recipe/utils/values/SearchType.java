package teamproject.capstone.recipe.utils.values;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {
    RECIPE_DETAILS("detail", "rcp_parts_dtls"),
    RECIPE_PARTS("part", "rcp_pat2"),
    RECIPE_WAY("way", "rcp_way2"),
    RECIPE_SEQUENCE("seq", "rcp_seq");

    private final String key;
    private final String value;
}
