package teamproject.capstone.recipe.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject.capstone.recipe.domain.user.User;
import teamproject.capstone.recipe.entity.user.UserEntity;
import teamproject.capstone.recipe.repository.user.UserRepository;
import teamproject.capstone.recipe.repository.user.UserUpdateRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserUpdateRepository userUpdateRepository;
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        UserEntity saveEntity = dtoToEntity(user);
        UserEntity savedEntity = userRepository.save(saveEntity);
        return entityToDto(savedEntity);
    }

    @Override
    public void delete(User user) {
        UserEntity deleteEntity = dtoToEntity(user);
        userRepository.delete(deleteEntity);
    }

    @Override
    public User findByEmail(String email) {
        Optional<UserEntity> byEmailEntity = userRepository.findByEmail(email);
        return byEmailEntity.map(this::entityToDto).orElse(null);
    }

    @Override
    public User findByUid(String uid) {
        Optional<UserEntity> byUidEntity = userRepository.findByUid(uid);
        return byUidEntity.map(this::entityToDto).orElse(null);
    }

    @Override
    public boolean isAppUser(User user) {
        Optional<UserEntity> byIdEntity = userRepository.findById(user.getId());
        return byIdEntity.filter(userEntity -> isUidExist(userEntity.getUid())).isPresent();
    }

    @Override
    public boolean isUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User updateUserBeApp(User user) {
        UserEntity updateEntity = dtoToEntity(user);
        UserEntity userEntity = userUpdateRepository.updateBeAppUser(updateEntity);
        return entityToDto(userEntity);
    }

    private boolean isUidExist(String uid) {
        return !uid.isEmpty();
    }
}
