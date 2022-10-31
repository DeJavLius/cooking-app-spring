package teamproject.capstone.recipe.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Cookrcp01 {
    @JsonProperty("RESULT")
    private Result result;
    @JsonProperty("row")
    private List<Row> row;
    @JsonProperty("total_count")
    private String totalCount;
}
