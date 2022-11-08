package teamproject.capstone.recipe.domain.user;

import lombok.Getter;
import teamproject.capstone.recipe.entity.user.UserEntity;
import teamproject.capstone.recipe.utils.property.Company;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String uid;
    private String company;

    public SessionUser(UserEntity user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.uid = user.getUid();
        this.company = user.getCompany().getCompany();
    }
}
