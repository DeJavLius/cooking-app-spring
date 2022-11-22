package teamproject.capstone.recipe.entity.recipe;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;

import javax.persistence.*;

@Entity(name = "recipe_favorite")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FavoriteRecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long recipeSeq;

    @Column
    private String userEmail;
}
