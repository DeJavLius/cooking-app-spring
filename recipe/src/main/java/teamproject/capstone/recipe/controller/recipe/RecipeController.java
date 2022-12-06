package teamproject.capstone.recipe.controller.recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.utils.api.json.FavoriteRecipe;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.domain.recipe.manual.RecipeManual;
import teamproject.capstone.recipe.domain.recipe.manual.RecipeManualImg;
import teamproject.capstone.recipe.domain.user.SessionUser;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.service.recipe.*;
import teamproject.capstone.recipe.utils.login.session.LoginSession;
import teamproject.capstone.recipe.utils.page.*;
import teamproject.capstone.recipe.utils.page.TotalValue;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/recipes")
@RequiredArgsConstructor
@Controller
@Slf4j
public class RecipeController {
    private final OpenRecipePageWithSearchService openRecipePageWithSearchService;
    private final OpenRecipeService openRecipeService;
    private final RecipeService recipeService;
    private final FavoriteService favoriteService;
    private final FavoriteRankService favoriteRankService;
    private final SearchWithPageHandler searchWithPageHandler;

    @GetMapping
    public String showAllRecipes(@LoginSession SessionUser user, PageCall pageCall, Search search, Model model) {
        Search value = Search.builder().name(search.getName()).seq(search.getSeq()).detail(search.getDetail()).part(search.getPart()).way(search.getWay()).build();
        SearchWithPageRequest searchWithPageRequest = searchWithPageHandler.choosePageWithSearch(search, pageCall.getPage(), pageCall.getSize());
        Sort sort = pageCall.getOrder().equals("") ? Sort.by("rcpNm").descending() : Sort.by("rcpNm").ascending();
        RecipePageResult<OpenRecipe, OpenRecipeEntity> openRecipeOpenRecipeEntityPageResult = openRecipePageWithSearchService.searchPageWithSortRecipes(value, searchWithPageHandler.searchPageWithSort(searchWithPageRequest, sort));

        List<Long> userFavoriteRecipe = new ArrayList<>();
        if (user != null) {
            userFavoriteRecipe = favoriteRankService.allFavoriteRecipe(user.getEmail());
        }

        model.addAttribute("user", user);
        model.addAttribute("userFavoriteList", userFavoriteRecipe);
        model.addAttribute("recipeTotal", TotalValue.getTotalCount());
        model.addAttribute("recipeList", openRecipeOpenRecipeEntityPageResult);
        return "recipe/recipeList";
    }

    @GetMapping("/detail/{id}")
    public String detailRecipe(@PathVariable Long id, @LoginSession SessionUser user, Model model) {
        boolean isFavorite = false;
        OpenRecipe recipe = openRecipeService.findRecipe(id);

        if (user != null) {
            model.addAttribute("user", user);

            FavoriteRecipe requestFavorite = FavoriteRecipe.builder().recipeSeq(recipe.getRcpSeq()).userEmail(user.getEmail()).build();
            isFavorite = !favoriteService.isFavoriteNotExist(requestFavorite);
        } else {
            model.addAttribute("user", null);
        }
        List<RecipeManual> recipeManuals = recipeService.recipeManualSplit(recipe);
        List<RecipeManualImg> recipeManualImages = recipeService.recipeManualImgSplit(recipe);

        model.addAttribute("recipe", recipe);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("recipeManuals", recipeManuals);
        model.addAttribute("recipeManualImages", recipeManualImages);
        return "recipe/recipeDetail";
    }
}
