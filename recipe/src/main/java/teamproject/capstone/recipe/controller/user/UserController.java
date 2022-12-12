package teamproject.capstone.recipe.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.Recommend;
import teamproject.capstone.recipe.domain.user.SessionUser;
import teamproject.capstone.recipe.domain.user.User;
import teamproject.capstone.recipe.service.recipe.FavoriteRankService;
import teamproject.capstone.recipe.service.user.UserService;
import teamproject.capstone.recipe.utils.login.session.LoginSession;
import teamproject.capstone.recipe.utils.page.PageCall;
import teamproject.capstone.recipe.utils.page.SearchWithPageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/cookers")
@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final FavoriteRankService favoriteRankService;
    private final SearchWithPageHandler<Favorite> searchWithPageHandler;
    private final UserService userService;

    @GetMapping("/info")
    public String userInfo(Model model, @LoginSession SessionUser user) {
        if (user != null) {
            User foundUser = userService.findByEmail(user.getEmail());
            List<Favorite> favorites = favoriteRankService.usersFavoriteRecipe(foundUser.getEmail());
            List<List<Favorite>> lists = searchWithPageHandler.pageRowRank(favorites);

            model.addAttribute("user", foundUser);
            model.addAttribute("usersFavorite", lists);
            return "user/userInfo";
        }
        return "index";
    }
}
