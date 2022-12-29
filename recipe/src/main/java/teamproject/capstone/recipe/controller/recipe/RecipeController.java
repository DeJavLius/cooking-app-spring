package teamproject.capstone.recipe.controller.recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.recipe.*;
import teamproject.capstone.recipe.domain.recipe.manual.RecipeManual;
import teamproject.capstone.recipe.domain.recipe.manual.RecipeManualImg;
import teamproject.capstone.recipe.domain.user.SessionUser;
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
    private final RecipeRecommendService recipeRecommendService;
    private final OpenRecipeService openRecipeService;
    private final RecipeService recipeService;
    private final FavoriteService favoriteService;
    private final FavoriteRankService favoriteRankService;
    private final SearchWithPageHandler<OpenRecipe> searchWithPageHandler;

    @GetMapping
    public String showAllRecipes(@LoginSession SessionUser user, PageCall pageCall, Search search, Model model) {
        List<Way> ways = recipeService.recipeWayValueFound();
        List<Part> parts = recipeService.recipePartValueFound();
        ways.add(0, new Way("All"));
        parts.add(0, new Part("All"));

        Sort sort = pageCall.getOrder().equals("f") ? Sort.by("favorite").descending() : Sort.by("id").ascending();
        Search value = Search.builder().name(search.getName()).seq(search.getSeq()).detail(search.getDetail()).part(search.getPart().equals("All") ? "" : search.getPart()).way(search.getWay().equals("All") ? "" : search.getWay()).build();
        SearchWithPageRequest searchWithPageRequest = searchWithPageHandler.choosePageWithSearch(search, pageCall.getPage(), pageCall.getSize());
        RecipePageResult<Favorite, Object[]> recipePageResult = openRecipePageWithSearchService.searchTuplePageWithSortRecipes(value, searchWithPageHandler.searchPageWithSort(searchWithPageRequest, sort));

        List<Long> favoriteSeq = new ArrayList<>();
        if (user != null) {
            favoriteSeq = favoriteRankService.usersFavoriteOnlySeq(user.getEmail());
        }

        model.addAttribute("user", user);
        model.addAttribute("ways", ways);
        model.addAttribute("parts", parts);
        model.addAttribute("favoriteSeqList", favoriteSeq);
        model.addAttribute("recipeTotal", TotalValue.getTotalCount());
        model.addAttribute("recipeList", recipePageResult);
        return "recipe/recipeList";
    }

    @GetMapping("/detail/{id}")
    public String detailRecipe(@PathVariable Long id, @LoginSession SessionUser user, Model model) {
        boolean isFavorite = false;
        OpenRecipe recipe = openRecipeService.findRecipe(id);

        if (user != null) {
            Favorite recipeFavorite = favoriteService.findRecipe(recipe.getRcpSeq(), user.getEmail());
            if (recipeFavorite.getId() > 0L) {
                isFavorite = true;
            }
        }

        List<RecipeManual> recipeManuals = recipeService.recipeManualSplit(recipe);
        List<RecipeManualImg> recipeManualImages = recipeService.recipeManualImgSplit(recipe);

        Search part = Search.builder().part(recipe.getRcpPat2()).build();
        List<Recommend> recommends = recommendRecipeList(part);

        model.addAttribute("user", user);
        model.addAttribute("recipe", recipe);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("recipeManuals", recipeManuals);
        model.addAttribute("recipeManualImages", recipeManualImages);
        model.addAttribute("relativeList", recommends);
        return "recipe/recipeDetail";
    }

    private List<Recommend> recommendRecipeList(Search search) {
        return recipeRecommendService.findRecommendRecipe(search);
    }
}
