package teamproject.capstone.recipe.utils.api.json;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Getter
@Slf4j
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Sequences {
    private List<Long> sequences;
}
