package teamproject.capstone.recipe.entity.user;

import lombok.*;
import teamproject.capstone.recipe.utils.values.Role;

import javax.persistence.*;

@Entity(name = "recipe_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String uid;

    @Column
    private Role role;

    public UserEntity update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
