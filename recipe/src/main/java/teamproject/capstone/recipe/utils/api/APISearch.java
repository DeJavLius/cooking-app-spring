package teamproject.capstone.recipe.utils.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class APISearch {
    private String type;
    private String keyword;
}
