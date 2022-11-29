package teamproject.capstone.recipe.utils.api.json.parts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import teamproject.capstone.recipe.utils.api.json.parts.Result;
import teamproject.capstone.recipe.utils.api.json.parts.Row;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OpenAPIRecipe {
    @JsonProperty("RESULT")
    private Result result;
    @JsonProperty("row")
    private List<Row> row;
    @JsonProperty("total_count")
    private String totalCount;
}
