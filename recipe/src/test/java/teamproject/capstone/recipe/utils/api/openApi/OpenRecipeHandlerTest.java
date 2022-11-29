package teamproject.capstone.recipe.utils.api.openApi;

import org.junit.jupiter.api.Test;
import teamproject.capstone.recipe.utils.api.json.parts.OpenAPIRecipe;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OpenRecipeHandlerTest {
    OpenAPIHandler openApiHandler = new OpenAPIHandler();

    @Test
    void requestAllOpenAPI() {
        // given, when
        List<OpenAPIRecipe> openAPIRecipes = openApiHandler.requestAllOpenAPI();

        // then
        assertThat(openAPIRecipes.size()).isEqualTo(2);
        assertThat(openAPIRecipes.get(0).getRow().size() + openAPIRecipes.get(1).getRow().size()).isEqualTo(Integer.parseInt(openAPIRecipes.get(0).getTotalCount()));
    }
}