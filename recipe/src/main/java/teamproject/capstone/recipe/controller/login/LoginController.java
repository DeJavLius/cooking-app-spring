package teamproject.capstone.recipe.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import teamproject.capstone.recipe.utils.firebase.FirebaseUserManager;

@RequestMapping("/account")
@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {
    private final FirebaseUserManager firebaseUserManager;

    @GetMapping
    public String loginPage() {
        return "login/recipeLogin";
    }

    @GetMapping("/login/success")
    public String loginSuccessPage() {
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/login")
    public String firebaseLoginTest() {
        try {
            boolean appUser = firebaseUserManager.isAppUserByEmail("123456@naver.com");
            log.info("app User check : {}", appUser);
            return "true";
        } catch (Exception e) {
            return "false";
        }
    }
}
