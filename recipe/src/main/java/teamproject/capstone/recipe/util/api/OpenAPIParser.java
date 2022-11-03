package teamproject.capstone.recipe.util.api;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import teamproject.capstone.recipe.domain.api.OpenAPIMeta;
import teamproject.capstone.recipe.domain.api.OpenAPIRecipe;

import java.io.IOException;
import java.net.URL;

@Slf4j
class OpenAPIParser {
    private static final OpenAPIParser INSTANCE = new OpenAPIParser();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private OpenAPIParser() { }

    public static OpenAPIParser getInstance() {
        return INSTANCE;
    }

    public OpenAPIRecipe parseURLToCookRecipe(URL apiUrl) throws IOException {
        return objectMapper.readValue(apiUrl, OpenAPIMeta.class).getOpenRecipe();
    }
}
