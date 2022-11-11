package teamproject.capstone.recipe.utils.api;

import lombok.Getter;

@Getter
public final class APIPage {
    private final int page;
    private final int size;

    private APIPage(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public static APIPage otherPage(int page, int size) {
        int PAGE_NOW = 1;
        page -= PAGE_NOW;

        if (page <= 0) {
            page = 0;
        }
        return new APIPage(page, size);
    }
}
