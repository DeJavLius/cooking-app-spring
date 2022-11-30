package teamproject.capstone.recipe.controller.recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.service.recipe.OpenAPIPageWithSearchService;
import teamproject.capstone.recipe.utils.page.*;

@RequestMapping("/recipes")
@RequiredArgsConstructor
@Controller
@Slf4j
public class RecipeController {
    private final OpenAPIPageWithSearchService openAPIPageWithSearchService;
    private final SearchWithPageHandler searchWithPageHandler;

    private final String DEFAULT_VALUE = "";
    private final String DEFAULT_SEQ = "0";

    @GetMapping
    public String showAllRecipes(PageCall pageCall, @RequestParam(defaultValue = DEFAULT_VALUE) String name, @RequestParam(defaultValue = DEFAULT_VALUE) String detail, @RequestParam(defaultValue = DEFAULT_VALUE) String part, @RequestParam(defaultValue = DEFAULT_VALUE) String way, @RequestParam(defaultValue = DEFAULT_SEQ) String seq, Model model) {
        log.info("page call value check : {}, {}", pageCall.getPage(), pageCall.getSize());
        SearchWrapper search = new SearchWrapper.Builder().name(name).seq(seq).detail(detail).part(part).way(way).build();
        SearchWithPageRequest searchWithPageRequest = searchWithPageHandler.choosePageWithSearch(search, pageCall.getPage(), pageCall.getSize());
        Sort sort = pageCall.getOrder().equals("") ? Sort.by("rcpNm").descending() : Sort.by("rcpNm").ascending();
        PageResult<OpenRecipe, OpenRecipeEntity> openRecipeOpenRecipeEntityPageResult = openAPIPageWithSearchService.searchPageWithSortRecipes(searchWithPageRequest.getSearchType().getSearchList(), searchWithPageHandler.searchPageWithSort(searchWithPageRequest, sort));

        log.info("value check of open Recipe Result : {}", openRecipeOpenRecipeEntityPageResult);

        model.addAttribute("recipeList", openRecipeOpenRecipeEntityPageResult);
        return "recipe/recipeList";
    }

    @GetMapping("search")
    public String searchRecipes() {
        return "";
    }
}
