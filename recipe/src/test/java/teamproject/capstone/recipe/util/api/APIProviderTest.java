package teamproject.capstone.recipe.util.api;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import teamproject.capstone.recipe.domain.api.CookRecipe;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class APIProviderTest {
    public APIProvider apiProvider = new APIProvider();

    @Test
    void requestAllOpenAPI() {
        // given
        List<CookRecipe> cookRecipes = new ArrayList<>();

        // when
        cookRecipes = apiProvider.requestAllOpenAPI();
//        ReflectionTestUtils.invokeMethod(apiProvider, "takeAllCookRecipes", cookRecipes);

        assertThat(cookRecipes.size()).isEqualTo(2);
    }

    @Test
    void privateCookRecipeRequestTest() {
        ReflectionTestUtils.invokeMethod(apiProvider, "cookRecipeRequest");
    }
}