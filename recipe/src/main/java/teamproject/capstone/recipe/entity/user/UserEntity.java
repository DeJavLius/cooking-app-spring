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
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String uid;

    @Column
    private Company company;

    @Column
    private Role role;

    @Builder
    public UserEntity(String name, String email, String uid, Company company, Role role) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.company = company;
        this.role = role;
    }

    public UserEntity(Long id, String name, String email, String uid) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public UserEntity update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
