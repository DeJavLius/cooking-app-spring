package teamproject.capstone.recipe.utils.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class OpenRecipeManagerTest {
    public OpenAPIProvider openApiProvider = OpenAPIProvider.getInstance();

    private static final String RECIPE_OPEN_API = "https://openapi.foodsafetykorea.go.kr";
    private static final String API_KEY = "ac3c23441c1c4a1e9696";

    @Test
    void urlIndexRangeScanTest() throws MalformedURLException {
        int request = 1058;

        // given
        openApiProvider.urlIndexRangeScan(request, request);

        // when
        URL apiUrl = openApiProvider.getApi().getAPIUrl();

        // then
        assertThat(apiUrl).isEqualTo(new URL(RECIPE_OPEN_API + "/api/" + API_KEY +
                "/COOKRCP01/json/" + request + "/" + request));
    }
}