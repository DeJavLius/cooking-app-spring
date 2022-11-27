package teamproject.capstone.recipe.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {
    private final ApplicationContext applicationContext;

    public FirebaseConfig(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        firebaseApp();
//        try {
//            ListUsersPage page = testsFunc();
//            while (page != null) {
//                for (ExportedUserRecord user : page.getValues()) {
//                    log.info("User: {}", user.getEmail());
//                }
//                page = page.getNextPage();
//            }
//
//            UserRecord user = testOfFunc();
//            log.info("User found: {}", user.getEmail());
//            log.info("User found: {}", user.getUid());
//        } catch (Exception e) {
//            log.error("error of email found", e);
//        }
    }

    private void firebaseApp() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(this.firebaseSettingFromJsonFileInput()))
                    .setStorageBucket("ecorecipes-5f00b.appspot.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FileInputStream firebaseSettingFromJsonFile() throws FileNotFoundException {
        return new FileInputStream("src/main/resources/ecorecipes-5f00b-firebase-adminsdk-c962f-26f07a6fa6.json");
    }

    private InputStream firebaseSettingFromJsonFileInput() throws IOException {
        return this.applicationContext.getResource("classpath:ecorecipes-5f00b-firebase-adminsdk-c962f-26f07a6fa6.json").getInputStream();
    }

//    private UserRecord testFunc() throws FirebaseAuthException {
//        return FirebaseAuth.getInstance().getUserByEmail("123456@naver.com");
//    }
//
//    private UserRecord testOfFunc() throws FirebaseAuthException {
//        return FirebaseAuth.getInstance().getUserByEmail("gh1369tjd@naver.com");
//    }
//
//    private ListUsersPage testsFunc() throws FirebaseAuthException {
//        return FirebaseAuth.getInstance().listUsers(null);
//    }
}
