package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.api.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.service.api.OpenAPIService;
import teamproject.capstone.recipe.utils.api.OpenAPIHandler;
import teamproject.capstone.recipe.utils.OpenAPISerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
@RestController
public class OpenAPIController {
    private final OpenAPIService openAPIService;
    private final OpenAPIHandler openApiHandler;

    private final int DEFAULT_SIZE = 10;

    @GetMapping("/v1")
    public List<OpenAPIRecipe> responseOpenAPI(@RequestParam String page, @RequestParam String size) {
        return null;
    }

    @PostMapping("/v1/save")
    public void saveOpenAPI() {
        List<OpenAPIRecipe> openAPIRecipes = openApiHandler.requestAllOpenAPI();
        List<OpenRecipe> totalRecipes = new ArrayList<>();

        for (OpenAPIRecipe cr : openAPIRecipes) {
            totalRecipes.addAll(cr.getRow().stream().map(OpenAPISerializer::rowToOpenRecipe).collect(Collectors.toList()));
        }

        openAPIService.createAll(totalRecipes);
    }
}
