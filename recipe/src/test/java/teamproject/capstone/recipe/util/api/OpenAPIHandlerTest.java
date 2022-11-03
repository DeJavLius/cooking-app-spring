package teamproject.capstone.recipe.util.api;

import org.junit.jupiter.api.Test;
import teamproject.capstone.recipe.domain.api.OpenRecipe;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OpenAPIHandlerTest {
    OpenAPIHandler openApiHandler = new OpenAPIHandler();

    @Test
    void requestAllOpenAPI() {
        // given
        List<OpenRecipe> openRecipes = new ArrayList<>();

        // when
        openRecipes = openApiHandler.requestAllOpenAPI();

        // then
        assertThat(openRecipes.size()).isEqualTo(2);
    }
}