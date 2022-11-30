package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
import teamproject.capstone.recipe.utils.values.TotalValue;

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
    private final OpenRecipeFavoriteService openRecipeFavoriteService;
    private final FavoriteRecipeRankService favoriteRecipeRankService;
    private final OpenAPIHandler openApiHandler;
    private final SearchWithPageHandler searchWithPageHandler;

    private final String DEFAULT_PAGE = "1";
    private final String DEFAULT_SIZE = "10";
    private final String DEFAULT_VALUE = "";
    private final String DEFAULT_SEQ = "0";

    @GetMapping(value = "/v1", produces = "application/json; charset=UTF-8")
    public RecipeData responseOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        PageRequest pageRequest = searchWithPageHandler.choosePage(page, size);
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openRecipePageWithSearchService.allAPIDataSources(pageRequest);

        boolean isEnd = page == TotalValue.getTotalCount();
        Meta metaInfo = MetaDelegator.metaGenerator(isEnd, openRecipeAPIPageResult.getTotalPage(), TotalValue.getTotalCount());

        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipeAPIPageResult.getDtoList())
                .build();
    }

    @GetMapping(value = "/v1/search/find-only", produces = "application/json; charset=UTF-8")
    public RecipeData responseSearchAndOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_VALUE) String name, @RequestParam(defaultValue = DEFAULT_VALUE) String detail, @RequestParam(defaultValue = DEFAULT_VALUE) String part, @RequestParam(defaultValue = DEFAULT_VALUE) String way, @RequestParam(defaultValue = DEFAULT_SEQ) String seq) {
        SearchWithPageRequest searchWithPageRequest = searchWithPageHandler.choosePageWithSearch(new SearchWrapper.Builder().name(name).detail(detail).part(part).seq(seq).way(way).build(), page, size);
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openRecipePageWithSearchService.searchAndAPIDataSources(searchWithPageRequest.getSearchType().getSearchList(), searchWithPageRequest.detailOf());

        boolean isEnd = page == TotalValue.getTotalCount();
        Meta metaInfo = MetaDelegator.metaGenerator(isEnd, openRecipeAPIPageResult.getTotalPage(), TotalValue.getTotalCount());

        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipeAPIPageResult.getDtoList())
                .build();
    }

    @GetMapping(value = "/v1/search/find-with", produces = "application/json; charset=UTF-8")
    public RecipeData responseSearchOrOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_VALUE) String name, @RequestParam(defaultValue = DEFAULT_VALUE) String detail, @RequestParam(defaultValue = DEFAULT_VALUE) String part, @RequestParam(defaultValue = DEFAULT_VALUE) String way, @RequestParam(defaultValue = DEFAULT_SEQ) String seq) {
        SearchWithPageRequest searchWithPageRequest = searchWithPageHandler.choosePageWithSearch(new SearchWrapper.Builder().name(name).detail(detail).part(part).seq(seq).way(way).build(), page, size);
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openRecipePageWithSearchService.searchOrAPIDataSources(searchWithPageRequest.getSearchType().getSearchList(), searchWithPageRequest.detailOf());

        boolean isEnd = page == TotalValue.getTotalCount();
        Meta metaInfo = MetaDelegator.metaGenerator(isEnd, openRecipeAPIPageResult.getTotalPage(), TotalValue.getTotalCount());

        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipeAPIPageResult.getDtoList())
                .build();
    }

    @GetMapping(value = "/v1/recipes/rank", produces = "application/json; charset=UTF-8")
    public RecipeData responseFavoriteOpenAPI() {
        List<Long> favoriteRankRecipe = favoriteRecipeRankService.mostFavoriteRankRecipe();
        List<OpenRecipe> favoriteRecipes = openRecipeFavoriteService.rankFavoriteRecipe(favoriteRankRecipe);

        Meta metaInfo = MetaDelegator.metaGenerator(true, favoriteRecipes.size(), 0);

        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(favoriteRecipes)
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
