package teamproject.capstone.recipe.repository.user;

import teamproject.capstone.recipe.entity.user.UserEntity;

public interface UserUpdateRepository {
    UserEntity updateBeAppUser(UserEntity updateEntity);
}
