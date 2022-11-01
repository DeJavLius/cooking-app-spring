package teamproject.capstone.recipe.util.api;

import java.net.URL;

class OpenAPI {
    private URL APIUrl;

    OpenAPI(URL APIUrl) {
        this.APIUrl = APIUrl;
    }

    public void setAPIUrl(URL APIUrl) {
        this.APIUrl = APIUrl;
    }

    public URL getAPIUrl() {
        return APIUrl;
    }
}
