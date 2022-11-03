package teamproject.capstone.recipe.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OpenRecipe {
    @JsonProperty("RESULT")
    private Result result;
    @JsonProperty("row")
    private List<Row> row;
    @JsonProperty("total_count")
    private String totalCount;
}
