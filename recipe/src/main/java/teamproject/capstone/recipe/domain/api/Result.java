package teamproject.capstone.recipe.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Result {
    @JsonProperty("CODE")
    private String code;
    @JsonProperty("MSG")
    private String msg;
}
