package teamproject.capstone.recipe.util.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import teamproject.capstone.recipe.domain.api.CookRecipe;

import java.io.IOException;
import java.net.URL;

@Slf4j
class APIParser {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final APIManager apiManager = new APIManager();

    public CookRecipe parseURLToCookRecipe() throws IOException {
        URL apiUrl = apiManager.getApi().getAPIUrl();
        return objectMapper.readValue(apiUrl, CookRecipe.class);
    }
}
