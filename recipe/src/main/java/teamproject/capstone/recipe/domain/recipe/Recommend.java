package teamproject.capstone.recipe.domain.recipe;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recommend {
    private Long id;
    private String image;
    private String name;
}
