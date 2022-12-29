package teamproject.capstone.recipe.service.user;

import teamproject.capstone.recipe.domain.user.User;
import teamproject.capstone.recipe.entity.user.UserEntity;

public interface UserService {
    User create(User user);
    void delete(User user);
    User findByEmail(String email);
    User findByUid(String uid);
    User updateUserBeApp(User user);

    boolean isAppUser(User user);
    boolean isUserExist(String email);

    default UserEntity dtoToEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .uid(user.getUid())
                .role(user.getRole())
                .build();
    }

    default User entityToDto(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .Uid(userEntity.getUid())
                .role(userEntity.getRole())
                .build();
    }
}
