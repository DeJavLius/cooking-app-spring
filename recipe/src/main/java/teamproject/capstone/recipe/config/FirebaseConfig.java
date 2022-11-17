package teamproject.capstone.recipe.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseAppSetting() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setStorageBucket("ecorecipes-5f00b.appspot.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth getFirebaseAuth() throws IOException {
        return FirebaseAuth.getInstance(firebaseAppSetting());
    }
}
