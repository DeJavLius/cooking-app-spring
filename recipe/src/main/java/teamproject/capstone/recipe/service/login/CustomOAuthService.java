//package teamproject.capstone.recipe.service.login;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.UserRecord;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import teamproject.capstone.recipe.domain.user.SessionUser;
//import teamproject.capstone.recipe.entity.user.UserEntity;
//import teamproject.capstone.recipe.repository.user.UserRepository;
//import teamproject.capstone.recipe.utils.login.attributes.OAuthAttributes;
//
//import javax.servlet.http.HttpSession;
//import java.util.Collections;
//import java.util.concurrent.ExecutionException;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class CustomOAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//    private final UserRepository userRepository;
//    private final HttpSession httpSession;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuthAttributes attributes = userInformationLoad(userRequest);
//
//        UserEntity user = saveOrUpdate(attributes);
//        httpSession.setAttribute("user", new SessionUser(user));
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey());
//    }
//
//    private OAuthAttributes userInformationLoad(OAuth2UserRequest userRequest) {
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        String userNameAttributeName = userRequest.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//        String uid = "";
//
//        try {
//            uid = firebaseRegister(oAuth2User.getAttributes().get("email").toString());
//        } catch (ExecutionException | InterruptedException ex) {
//            log.info("error occur cause of no user data found in firebase Users data.", ex);
//            throw new OAuth2AuthenticationException("UNAUTHORIZED_CLIENT");
//        }
//
//        return OAuthAttributes.of(userNameAttributeName, oAuth2User.getAttributes(), uid);
//    }
//
//    private String firebaseRegister(String email) throws ExecutionException, InterruptedException {
//        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmailAsync(email).get();
//        log.info("user data find successful");
//
//        return userRecord.getUid();
//    }
//
//    private UserEntity saveOrUpdate(OAuthAttributes attributes) {
//        UserEntity user = userRepository.findByEmail(attributes.getEmail())
//                .map(entity -> entity.update(attributes.getName()))
//                .orElse(attributes.toEntity());
//
//        return userRepository.save(user);
//    }
//}
