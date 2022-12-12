package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.service.recipe.*;
import teamproject.capstone.recipe.utils.api.*;
import teamproject.capstone.recipe.utils.api.json.*;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.utils.api.json.RecipeData;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.json.parts.OpenAPIRecipe;
import teamproject.capstone.recipe.utils.api.openApi.*;
import teamproject.capstone.recipe.utils.page.*;
import teamproject.capstone.recipe.utils.page.TotalValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
@RestController
public class OpenAPIController {
    private final OpenRecipeService openRecipeService;
    private final OpenRecipePageWithSearchService openRecipePageWithSearchService;
    private final OpenAPIHandler openApiHandler;
    private final SearchWithPageHandler<OpenRecipe> searchWithPageHandler;

    private final String DEFAULT_PAGE = "1";
    private final String DEFAULT_SIZE = "10";
    private final String DEFAULT_ORDER = "d";

    @GetMapping(value = "/v1", produces = "application/json; charset=UTF-8")
    public RecipeData responseOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_ORDER) String order) {
        Sort sort = order.equals("f") ? Sort.by("favorite").descending() : Sort.by("id").ascending();
        PageRequest pageRequest = searchWithPageHandler.choosePage(page, size, sort);
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openRecipePageWithSearchService.allAPIDataSources(pageRequest);

        boolean isEnd = page == TotalValue.getTotalCount();
        Meta metaInfo = MetaDelegator.metaGenerator(isEnd, openRecipeAPIPageResult.getTotalPage(), TotalValue.getTotalCount());

        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipeAPIPageResult.getDtoList())
                .build();
    }

    @GetMapping(value = "/v1/search/find-only", produces = "application/json; charset=UTF-8")
    public RecipeData responseSearchAndOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_ORDER) String order, Search value) {
        Sort sort = order.equals("f") ? Sort.by("favorite").descending() : Sort.by("id").ascending();
        SearchWithPageRequest searchWithPageRequest = searchWithPageHandler.choosePageWithSearch(value, page, size);
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openRecipePageWithSearchService.searchAndAPIDataSources(searchWithPageRequest.getSearch(), searchWithPageRequest.detailOfSort(sort));

        boolean isEnd = page == TotalValue.getTotalCount();
        Meta metaInfo = MetaDelegator.metaGenerator(isEnd, openRecipeAPIPageResult.getTotalPage(), TotalValue.getTotalCount());

        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipeAPIPageResult.getDtoList())
                .build();
    }

    @GetMapping(value = "/v1/search/find-with", produces = "application/json; charset=UTF-8")
    public RecipeData responseSearchOrOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_ORDER) String order, Search value) {
        Sort sort = order.equals("f") ? Sort.by("favorite").descending() : Sort.by("id").ascending();
        SearchWithPageRequest searchWithPageRequest = searchWithPageHandler.choosePageWithSearch(value, page, size);
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openRecipePageWithSearchService.searchOrAPIDataSources(searchWithPageRequest.getSearch(), searchWithPageRequest.detailOfSort(sort));

        boolean isEnd = page == TotalValue.getTotalCount();
        Meta metaInfo = MetaDelegator.metaGenerator(isEnd, openRecipeAPIPageResult.getTotalPage(), TotalValue.getTotalCount());

        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipeAPIPageResult.getDtoList())
                .build();
    }

    @GetMapping(value = "/v1/recipes/rank", produces = "application/json; charset=UTF-8")
    public RecipeData responseFavoriteOpenAPI() {
        List<OpenRecipe> openRecipes = openRecipePageWithSearchService.mostAndroidRecipe();

        Meta metaInfo = MetaDelegator.metaGenerator(true, openRecipes.size(), 0);

        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipes)
                .build();
    }

    @GetMapping("/v2/save")
    public String saveOpenAPI() {
        List<OpenAPIRecipe> openAPIRecipes = openApiHandler.requestAllOpenAPI();
        List<OpenRecipe> totalRecipes = new ArrayList<>();

        for (OpenAPIRecipe cr : openAPIRecipes) {
            totalRecipes.addAll(cr.getRow().stream().map(OpenAPIDelegator::rowToOpenRecipe).collect(Collectors.toList()));
        }

        openRecipeService.createAll(totalRecipes);

        return "데이터 저장 완료 원래 화면으로 돌아가세요. 테스트용 임시 url";
    }

    @GetMapping("/v2/delete/all")
    public String deleteOpenAPI() {
        openRecipeService.deleteAll();

        return "데이터 삭제 완료 원래 화면으로 돌아가세요. 테스트용 임시 url";
    }
}
