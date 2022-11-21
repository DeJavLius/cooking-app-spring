package teamproject.capstone.recipe.utils.firebase;

import com.google.firebase.auth.FirebaseAuthException;

public interface FirebaseUserManager {
    boolean isAppUser(String email) throws FirebaseAuthException;
}
