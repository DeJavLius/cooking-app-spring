package teamproject.capstone.recipe.utils.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Result {
    @JsonProperty("CODE")
    private String code;
    @JsonProperty("MSG")
    private String msg;
}
