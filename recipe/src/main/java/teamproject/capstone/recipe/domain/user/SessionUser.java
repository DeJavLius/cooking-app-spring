package teamproject.capstone.recipe.domain.user;

import lombok.Getter;
import teamproject.capstone.recipe.entity.user.UserEntity;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String uid;

    public SessionUser(UserEntity user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.uid = user.getUid();
    }
}
