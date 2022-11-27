package teamproject.capstone.recipe.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamproject.capstone.recipe.domain.user.SessionUser;
import teamproject.capstone.recipe.utils.login.session.LoginSession;

@RequestMapping("/cookers")
@Controller
public class UserController {

    @GetMapping("/info")
    public String userInfo(Model model, @LoginSession SessionUser user) {
        model.addAttribute("user", user);
        return "/user/userInfo";
    }
}
