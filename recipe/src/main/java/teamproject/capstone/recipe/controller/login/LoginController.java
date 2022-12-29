package teamproject.capstone.recipe.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.user.SessionUser;
import teamproject.capstone.recipe.utils.firebase.FirebaseUserManager;
import teamproject.capstone.recipe.utils.login.session.LoginSession;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/account")
@Slf4j
@Controller
public class LoginController {
    @GetMapping("/login/success")
    public String loginGetSuccessPage(@LoginSession SessionUser sUser) {
        log.info("start account login or success : GET");
        log.info("login user : {}", sUser.getEmail());
        return "redirect:/";
    }

    @PostMapping("/login/success")
    public String loginPostSuccessPage(@LoginSession SessionUser sUser) {
        log.info("start account login or success : POST");
        log.info("login user : {}", sUser.getEmail());
        return "redirect:/";
    }

//    @ResponseBody
//    @GetMapping("/login")
//    public String firebaseLoginTest() {
//        try {
//            boolean appUser = firebaseUserManager.isAppUserByEmail("123456@naver.com");
//            log.info("app User check : {}", appUser);
//            return "true";
//        } catch (Exception e) {
//            return "false";
//        }
//    }
}
