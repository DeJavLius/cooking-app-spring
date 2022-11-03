package teamproject.capstone.recipe.util.api;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import teamproject.capstone.recipe.domain.api.OpenRecipe;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OpenAPIProviderTest {
    public OpenAPIProvider openApiProvider = new OpenAPIProvider();

    @Test
    void requestAllOpenAPI() {
        // given
        List<OpenRecipe> openRecipes = new ArrayList<>();

        // when
        openRecipes = openApiProvider.requestAllOpenAPI();
//        ReflectionTestUtils.invokeMethod(apiProvider, "takeAllCookRecipes", cookRecipes);

        assertThat(openRecipes.size()).isEqualTo(2);
    }

    @Test
    void privateCookRecipeRequestTest() {
        ReflectionTestUtils.invokeMethod(openApiProvider, "cookRecipeRequest");
    }
}