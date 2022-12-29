package teamproject.capstone.recipe.utils.api.openApi;

import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
class OpenAPIProvider {
    private static final OpenAPIProvider INSTANCE = new OpenAPIProvider();
    private static final String RECIPE_OPEN_API = "https://openapi.foodsafetykorea.go.kr";
    private static final String API_KEY = "ac3c23441c1c4a1e9696";

    private OpenAPI openApi;

    private OpenAPIProvider() { }

    public static OpenAPIProvider getInstance() {
        return INSTANCE;
    }

    private URL requestOpenAPIJSON(int startIndex, int endIndex) throws MalformedURLException {
        return new URL(RECIPE_OPEN_API + "/api/" + API_KEY +
                "/COOKRCP01/json/" + startIndex + "/" + endIndex);
    }

    public void urlIndexRangeScan(int startIndex, int endIndex) {
        try {
            openApi = new OpenAPI(requestOpenAPIJSON(startIndex, endIndex));
            log.info("test of open Api : {}", openApi.getAPIUrl().toString());
        } catch (MalformedURLException mal) {
            mal.printStackTrace();
            log.error("wrong url or wrong api key");
        }
    }

    public OpenAPI getApi() {
        return openApi;
    }
}
