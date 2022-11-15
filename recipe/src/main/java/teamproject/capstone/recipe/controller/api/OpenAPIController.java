package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.utils.api.*;
import teamproject.capstone.recipe.utils.api.json.Meta;
import teamproject.capstone.recipe.utils.api.json.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.utils.api.json.RecipeData;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.service.api.OpenAPIPageService;
import teamproject.capstone.recipe.service.api.OpenAPIService;
import teamproject.capstone.recipe.utils.values.TotalValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
@RestController
public class OpenAPIController {
    private final OpenAPIService openAPIService;
    private final OpenAPIPageService openAPIPageService;
    private final OpenAPIHandler openApiHandler;
    private final APIPageHandler apiPageHandler;

    private final String DEFAULT_PAGE = "1";
    private final String DEFAULT_SIZE = "10";

    @GetMapping(value = "/v1", produces = "application/json; charset=UTF-8")
    public RecipeData responseOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        PageRequest pageRequest = apiPageHandler.choosePage(page, size);
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openAPIPageService.allAPIDataSources(pageRequest);

        boolean isEnd = page == TotalValue.getTotalCount();
        Meta metaInfo = MetaDelegator.metaGenerator(isEnd, openRecipeAPIPageResult.getTotalPage(), TotalValue.getTotalCount());

        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipeAPIPageResult.getDtoList())
                .build();
    }

    @GetMapping("/v1/save")
    public void saveOpenAPI() {
        List<OpenAPIRecipe> openAPIRecipes = openApiHandler.requestAllOpenAPI();
        List<OpenRecipe> totalRecipes = new ArrayList<>();

        for (OpenAPIRecipe cr : openAPIRecipes) {
            totalRecipes.addAll(cr.getRow().stream().map(OpenAPIDelegator::rowToOpenRecipe).collect(Collectors.toList()));
        }

        openAPIService.createAll(totalRecipes);
    }
}
