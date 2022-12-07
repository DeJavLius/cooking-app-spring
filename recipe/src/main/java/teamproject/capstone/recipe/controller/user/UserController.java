package teamproject.capstone.recipe.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamproject.capstone.recipe.domain.user.SessionUser;
import teamproject.capstone.recipe.domain.user.User;
import teamproject.capstone.recipe.service.recipe.FavoriteRankService;
import teamproject.capstone.recipe.service.user.UserService;
import teamproject.capstone.recipe.utils.login.session.LoginSession;
import teamproject.capstone.recipe.utils.page.PageCall;

import java.util.List;

@RequestMapping("/cookers")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final FavoriteRankService favoriteRankService;
    private final UserService userService;

    @GetMapping("/info")
    public String userInfo(Model model, PageCall pageCall, @LoginSession SessionUser user) {
        User foundUser = userService.findByEmail(user.getEmail());
//        List<Long> favoriteList = favoriteRankService.allFavoriteRecipe(user.getEmail());

        model.addAttribute("user", foundUser);
        return "/user/userInfo";
    }
}
