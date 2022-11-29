package teamproject.capstone.recipe.entity.recipe;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column
    private Long recipeSeq;

    @Column
    private String userEmail;
}
