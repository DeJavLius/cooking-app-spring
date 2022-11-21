package teamproject.capstone.recipe.service.user;

import teamproject.capstone.recipe.domain.user.User;

public interface UserService {
    User create(User user);
    void delete(User user);
    User findByEmail(User user);
    User findByUid(User user);
    boolean isAppUser(User user);
}
