package teamproject.capstone.recipe.util.api;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class OpenAPIManagerTest {
    public OpenAPIManager openApiManager = new OpenAPIManager();

    private static final String RECIPE_OPEN_API = "https://openapi.foodsafetykorea.go.kr";
    private static final String API_KEY = "ac3c23441c1c4a1e9696";

    @Test
    void urlIndexRangeScanTest() throws MalformedURLException {
        // given
        openApiManager.urlIndexRangeScan(1061, 1061);

        // when
        URL apiUrl = openApiManager.getApi().getAPIUrl();

        // then
        assertThat(apiUrl).isEqualTo(new URL(RECIPE_OPEN_API + "api/" + API_KEY +
                "COOKRCP01/json/" + 1061 + "/" + 1061));
    }
}