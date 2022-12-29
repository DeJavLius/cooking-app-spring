package teamproject.capstone.recipe.utils.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FirebaseUserManagerImpl implements FirebaseUserManager {
    @Override
    public boolean isAppUserByEmail(String email) {
        try {
            UserRecord userByEmail = FirebaseAuth.getInstance().getUserByEmail(email);
            return !userByEmail.getEmail().isEmpty();
        } catch (FirebaseAuthException e) {
            log.error("Can't found user value in Firebase", e);
            return false;
        }
    }

    @Override
    public boolean isAppUserByUid(String uid) {
        try {
            UserRecord userByUid = FirebaseAuth.getInstance().getUser(uid);
            return !userByUid.getUid().isEmpty();
        } catch (FirebaseAuthException e) {
            log.error("Can't found user value in Firebase", e);
            return false;
        }
    }

    @Override
    public String findEmailByUid(String uid) {
        try {
            UserRecord userByUid = FirebaseAuth.getInstance().getUser(uid);
            return userByUid.getEmail();
        } catch (FirebaseAuthException e) {
            log.error("Can't found user value in Firebase", e);
            return "";
        }
    }
}
