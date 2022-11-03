package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.api.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.domain.api.Row;
import teamproject.capstone.recipe.service.api.OpenAPIService;
import teamproject.capstone.recipe.util.api.OpenAPIHandler;
import teamproject.capstone.recipe.util.api.OpenAPISerializer;

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
    // provider
    /*
    public List<CookRecipe> requestAllOpenAPI() {
            try {
                CookRecipe needValueCheck = requestOpenAPIFromURL(apiManager.getApi().getAPIUrl());
                CookRecipe checkedData = apiError.cookRecipeRightValueCheck(needValueCheck);
                log.info("value request test : {}", checkedData);
                cookRecipes.add(checkedData);
            } catch (IllegalArgumentException e) {
                e.getStackTrace();
                break;
            }
    }

    private CookRecipe requestOpenAPIFromURL(URL apiUrl) {
        try {
            log.info("url check : {}", apiUrl);
            return apiParser.parseURLToCookRecipe(apiUrl);
        } catch (IOException e) {
            e.getStackTrace();
            log.error("wrong json value : can't parse Json to String need to check");
            throw new IllegalArgumentException();
        }
    }
     */

    // parser
    /*

    public CookRecipe parseURLToCookRecipe(URL apiUrl) throws IOException {
        log.info("test of apiURL : {}", apiUrl);
        log.info("pares data test : {}", objectMapper.readValue(apiUrl, CookRecipeTaker.class));
        return objectMapper.readValue(apiUrl, CookRecipeTaker.class).getCookRecipe();
    }
     */
}
