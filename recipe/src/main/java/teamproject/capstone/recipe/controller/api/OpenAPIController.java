package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.utils.api.json.Meta;
import teamproject.capstone.recipe.utils.api.json.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.utils.api.json.RecipeData;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.service.api.OpenAPIPageService;
import teamproject.capstone.recipe.service.api.OpenAPIService;
import teamproject.capstone.recipe.utils.APIPageResult;
import teamproject.capstone.recipe.utils.api.OpenAPIHandler;
import teamproject.capstone.recipe.utils.OpenAPISerializer;
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

    private final String DEFAULT_PAGE = "1";
    private final String DEFAULT_SIZE = "10";

    @GetMapping("/v1")
    public RecipeData responseOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        int PAGE_NOW = 1;
        page -= PAGE_NOW;

        if (page <= 0) {
            page = 0;
        }
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openAPIPageService.allAPIDataSources(PageRequest.of(page, size));

        Meta metaInfo = Meta.builder()
                .is_end(page == TotalValue.getTotalCount())
                .pageable_count(openRecipeAPIPageResult.getTotalPage())
                .total_count(TotalValue.getTotalCount())
                .build();

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
            totalRecipes.addAll(cr.getRow().stream().map(OpenAPISerializer::rowToOpenRecipe).collect(Collectors.toList()));
        }

        openAPIService.createAll(totalRecipes);
    }
}
