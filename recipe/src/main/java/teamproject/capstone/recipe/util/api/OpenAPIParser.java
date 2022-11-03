package teamproject.capstone.recipe.util.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import teamproject.capstone.recipe.domain.api.OpenRecipe;

import java.io.IOException;
import java.net.URL;

@Slf4j
class OpenAPIParser {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OpenAPIManager openApiManager = new OpenAPIManager();

    public OpenRecipe parseURLToCookRecipe() throws IOException {
        URL apiUrl = openApiManager.getApi().getAPIUrl();
        return objectMapper.readValue(apiUrl, OpenRecipe.class);
    }
}
