package teamproject.capstone.recipe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.Recommend;
import teamproject.capstone.recipe.domain.user.SessionUser;
import teamproject.capstone.recipe.service.recipe.FavoriteRankService;
import teamproject.capstone.recipe.service.recipe.RecipeRecommendService;
import teamproject.capstone.recipe.utils.login.session.LoginSession;
import teamproject.capstone.recipe.utils.page.Search;
import teamproject.capstone.recipe.utils.page.SearchWithPageHandler;

import java.util.*;

@RequestMapping("/")
@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final FavoriteRankService favoriteRankService;
    private final RecipeRecommendService recipeRecommendService;
    private final SearchWithPageHandler<Favorite> searchWithPageHandler;

    @GetMapping("test")
    public String testPage() {
        return "test";
    }

    @GetMapping
    public String homePage(Model model, @LoginSession SessionUser user) {
        List<Favorite> favorites = favoriteRankService.mostFavoriteRecipe();
        List<List<Favorite>> rankRecipe = new ArrayList<>();
        if (user != null) {
            model.addAttribute("user", user);
        }

        String way = "굽기";
        String part = "반찬";
        Search waySearch = Search.builder().way("굽기").build();
        Search partSearch = Search.builder().part("반찬").build();
        if (favorites.size() > 0) {
            rankRecipe = searchWithPageHandler.pageRowRank(favorites);

            Favorite bestFavorite = favorites.get(0);
            way = bestFavorite.getRecipeWay();
            part = bestFavorite.getRecipePart();
            waySearch = waySearch(way);
            partSearch = partSearch(part);
        }

        List<Recommend> wayRecommend = recommendRecipeList(waySearch);
        List<Recommend> partRecommend = recommendRecipeList(partSearch);

        model.addAttribute("way", way);
        model.addAttribute("part", part);
        model.addAttribute("rank_recipes", rankRecipe);
        model.addAttribute("wayList", wayRecommend);
        model.addAttribute("partList", partRecommend);
        return "index";
    }

    private List<Recommend> recommendRecipeList(Search search) {
        return recipeRecommendService.findRecommendRecipe(search);
    }

    private Search waySearch(String way) {
        return Search.builder().way(way).build();
    }

    private Search partSearch(String part) {
        return Search.builder().part(part).build();
    }
}
