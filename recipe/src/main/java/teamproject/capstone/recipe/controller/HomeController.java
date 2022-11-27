package teamproject.capstone.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.domain.user.SessionUser;
import teamproject.capstone.recipe.domain.user.User;
import teamproject.capstone.recipe.service.api.OpenAPIService;
import teamproject.capstone.recipe.service.recipe.FavoriteRecipeRankService;
import teamproject.capstone.recipe.service.recipe.FavoriteRecipeService;
import teamproject.capstone.recipe.utils.login.session.LoginSession;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final OpenAPIService openAPIService;
    private final FavoriteRecipeRankService favoriteRecipeRankService;

    @GetMapping("test")
    public String testPage() {
        return "test";
    }

    @GetMapping
    public String homePage(Model model, @LoginSession SessionUser user) {

        List<Long> favoriteRankRecipe = favoriteRecipeRankService.mostFavoriteRankRecipe();
        List<OpenRecipe> openRecipes = rankFavoriteRecipe(favoriteRankRecipe);
        List<List<OpenRecipe>> rankRecipe = new ArrayList<>();
        if (user != null) {
            log.info("login test : {}", user.getEmail());
            model.addAttribute("user", user);
        }

        if (openRecipes.size() > 0) {
            rankRecipe = rotatingPageRank(openRecipes);
        }

        model.addAttribute("rank_recipes", rankRecipe);
        return "index";
    }

    private List<OpenRecipe> rankFavoriteRecipe(List<Long> sequences) {
        List<OpenRecipe> resultOfRank = new ArrayList<>();

        for (Long seq : sequences) {
            resultOfRank.add(openAPIService.findByRecipeSeq(seq));
        }

        return resultOfRank;
    }

    List<List<OpenRecipe>> rotatingPageRank(List<OpenRecipe> openRecipes) {
        List<List<OpenRecipe>> rankRecipe = new ArrayList<>();

        int start = 0;
        int midIndex = 4;
        int middle = 5;
        int recipesSize = openRecipes.size();
        if (recipesSize > middle) {
            rankRecipe.add(openRecipes.subList(start, midIndex));
            start = middle;
        }

        rankRecipe.add(openRecipes.subList(start, recipesSize));
        return rankRecipe;
    }
}
