package teamproject.capstone.recipe.entity.recipe;

import lombok.*;

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
    private Long id;

    @Column
    private Long recipe_seq;

    @Column
    private String user_email;
}
