package teamproject.capstone.recipe.utils.page;

import lombok.Getter;

@Getter
public final class PageDetailRequest {
    private final int page;
    private final int size;

    private PageDetailRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public static PageDetailRequest otherPage(int page, int size) {
        int PAGE_NOW = 1;
        page -= PAGE_NOW;

        if (page <= 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        return new PageDetailRequest(page, size);
    }
}
