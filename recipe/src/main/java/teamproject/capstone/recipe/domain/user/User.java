package teamproject.capstone.recipe.domain.user;

import lombok.*;
import teamproject.capstone.recipe.utils.values.Role;

@ToString
@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String Uid;
    private String name;
    private String email;
    private Role role;
}
