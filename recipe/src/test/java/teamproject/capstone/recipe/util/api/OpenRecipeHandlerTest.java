package teamproject.capstone.recipe.util.api;

import org.junit.jupiter.api.Test;
import teamproject.capstone.recipe.domain.api.OpenAPIRecipe;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OpenRecipeHandlerTest {
    OpenAPIHandler openApiHandler = new OpenAPIHandler();

    @Test
    void requestAllOpenAPI() {
        // given
        List<OpenAPIRecipe> openAPIRecipes = new ArrayList<>();

        // when
        openAPIRecipes = openApiHandler.requestAllOpenAPI();

        // then
        assertThat(openAPIRecipes.size()).isEqualTo(2);
    }
}