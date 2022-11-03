package teamproject.capstone.recipe.domain.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Meta {
    @JsonProperty("total_count")
    private Integer total_count;
    @JsonProperty("pageable_count")
    private Integer pageable_count;
    @JsonProperty("is_end")
    private boolean is_end;
}
