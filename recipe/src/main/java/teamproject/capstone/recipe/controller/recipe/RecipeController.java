package teamproject.capstone.recipe.controller.recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.service.recipe.OpenRecipePageWithSearchService;
import teamproject.capstone.recipe.service.recipe.OpenRecipeService;
import teamproject.capstone.recipe.utils.page.*;
import teamproject.capstone.recipe.utils.values.TotalValue;

@RequestMapping("/recipes")
@RequiredArgsConstructor
@Controller
@Slf4j
public class RecipeController {
    private final OpenRecipePageWithSearchService openRecipePageWithSearchService;
    private final OpenRecipeService openRecipeService;
    private final SearchWithPageHandler searchWithPageHandler;

    private final String DEFAULT_VALUE = "";
    private final String DEFAULT_SEQ = "0";

    @GetMapping
    public String showAllRecipes(PageCall pageCall, @RequestParam(defaultValue = DEFAULT_VALUE) String name, @RequestParam(defaultValue = DEFAULT_VALUE) String detail, @RequestParam(defaultValue = DEFAULT_VALUE) String part, @RequestParam(defaultValue = DEFAULT_VALUE) String way, @RequestParam(defaultValue = DEFAULT_SEQ) String seq, Model model) {
        SearchWrapper search = new SearchWrapper.Builder().name(name).seq(seq).detail(detail).part(part).way(way).build();
        SearchWithPageRequest searchWithPageRequest = searchWithPageHandler.choosePageWithSearch(search, pageCall.getPage(), pageCall.getSize());
        Sort sort = pageCall.getOrder().equals("") ? Sort.by("rcpNm").descending() : Sort.by("rcpNm").ascending();
        RecipePageResult<OpenRecipe, OpenRecipeEntity> openRecipeOpenRecipeEntityPageResult = openRecipePageWithSearchService.searchPageWithSortRecipes(searchWithPageRequest.getSearchType().getSearchList(), searchWithPageHandler.searchPageWithSort(searchWithPageRequest, sort));

        model.addAttribute("recipeTotal", TotalValue.getTotalCount());
        model.addAttribute("recipeList", openRecipeOpenRecipeEntityPageResult);
        return "recipe/recipeList";
    }

    @GetMapping("/detail/{id}")
    public String detailRecipe(@PathVariable Long id, Model model) {
        OpenRecipe recipe = openRecipeService.findRecipe(id);
        model.addAttribute("recipe", recipe);
        return "recipe/recipeDetail";
    }
}
