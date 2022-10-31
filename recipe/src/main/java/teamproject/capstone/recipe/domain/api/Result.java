package teamproject.capstone.recipe.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty("CODE")
    private String code;
    @JsonProperty("MSG")
    private String msg;
}
