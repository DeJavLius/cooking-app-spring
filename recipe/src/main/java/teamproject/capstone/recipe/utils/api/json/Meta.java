package teamproject.capstone.recipe.utils.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Meta {
    @JsonProperty("end")
    private boolean end;
    @JsonProperty("total_count")
    private Integer total_count;
    @JsonProperty("pageable_count")
    private Integer pageable_count;
}
