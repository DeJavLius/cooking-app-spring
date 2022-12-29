package teamproject.capstone.recipe.utils.firebase;

import com.google.firebase.auth.FirebaseAuthException;

public interface FirebaseUserManager {
    boolean isAppUserByEmail(String email);
    boolean isAppUserByUid(String uid);

    String findEmailByUid(String uid);
}
