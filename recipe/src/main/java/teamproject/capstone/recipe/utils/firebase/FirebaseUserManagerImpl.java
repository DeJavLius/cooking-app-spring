package teamproject.capstone.recipe.utils.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Component;

@Component
public class FirebaseUserManagerImpl implements FirebaseUserManager {
    @Override
    public boolean isAppUser(String email) throws FirebaseAuthException {
        UserRecord userByEmail = FirebaseAuth.getInstance().getUserByEmail(email);
        return !userByEmail.getEmail().isEmpty();
    }
}
