package teamproject.capstone.recipe.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/account")
@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {
    @GetMapping
    public String loginPage() {
        return "login/recipeLogin";
    }

    @GetMapping("/login/success")
    public String loginSuccessPage() {
        return "redirect:/";
    }
}
