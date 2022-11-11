package teamproject.capstone.recipe.entity.user;

import lombok.*;
import teamproject.capstone.recipe.utils.property.Company;
import teamproject.capstone.recipe.utils.property.Role;

import javax.persistence.*;

@Entity(name = "recipe_user")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Company company;

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
