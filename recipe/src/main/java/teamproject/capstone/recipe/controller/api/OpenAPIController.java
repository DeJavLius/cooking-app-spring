package teamproject.capstone.recipe.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.domain.api.Row;
import teamproject.capstone.recipe.util.api.OpenAPIProvider;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class OpenAPIController {
    // /api/ac3c23441c1c4a1e9696/COOKRCP01/json/1/1061
    private final OpenAPIService openAPIService;

    private final OpenAPIProvider openApiProvider = new OpenAPIProvider();

    @GetMapping("/v1")
    public List<OpenRecipe> responseOpenAPI(@RequestParam String page, @RequestParam String size) {
        return null;
    }

    @PostMapping("/v1/save")
    public void saveOpenAPI() {
        List<OpenRecipe> openRecipes = openApiProvider.requestAllOpenAPI();
        List<Row> totalRecipes = new ArrayList<>();

        for (OpenRecipe cr : openRecipes) {
            totalRecipes.addAll(cr.getRow());
        }
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
    static class CookRecipeTaker {
        @JsonProperty("COOKRCP01")
        CookRecipe cookRecipe;

        public CookRecipe getCookRecipe() {
            return this.cookRecipe;
        }
    }

    public CookRecipe parseURLToCookRecipe(URL apiUrl) throws IOException {
        log.info("test of apiURL : {}", apiUrl);
        log.info("pares data test : {}", objectMapper.readValue(apiUrl, CookRecipeTaker.class));
        return objectMapper.readValue(apiUrl, CookRecipeTaker.class).getCookRecipe();
    }
     */
}
