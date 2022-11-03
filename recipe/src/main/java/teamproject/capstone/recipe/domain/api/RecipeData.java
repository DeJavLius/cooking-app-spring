package teamproject.capstone.recipe.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RecipeData {
    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("row")
    private List<Row> row;
}
