package teamproject.capstone.recipe.domain.recipe;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Favorite {
    private Long id;
    private Long recipeId;
    private Long recipeSeq;
    private String recipeMainImage;
    private String recipeName;
    private String recipeWay;
    private String recipePart;
    private String userEmail;
    @Builder.Default
    private Long count = 0L;
}