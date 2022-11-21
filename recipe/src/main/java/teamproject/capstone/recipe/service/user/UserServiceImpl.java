package teamproject.capstone.recipe.service.user;

import org.springframework.stereotype.Service;
import teamproject.capstone.recipe.domain.user.User;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User findByEmail(User user) {
        return null;
    }

    @Override
    public User findByUid(User user) {
        return null;
    }

    @Override
    public boolean isAppUser(User user) {
        return false;
    }
}
