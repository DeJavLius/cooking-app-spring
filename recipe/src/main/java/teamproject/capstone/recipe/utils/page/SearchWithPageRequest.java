package teamproject.capstone.recipe.utils.page;

import org.springframework.data.domain.*;

public class SearchWithPageRequest {
    private final PageDetailRequest pageDetail;
    private final Search search;

    private SearchWithPageRequest(Search search, PageDetailRequest pageDetail) {
        this.search = search;
        this.pageDetail = pageDetail;
    }

    public static SearchWithPageRequest searchPageRequest(Search search, PageDetailRequest pageDetail) {
        return new SearchWithPageRequest(search, pageDetail);
    }

    public PageRequest detailOf() {
        return PageRequest.of(pageDetail.getPage(), pageDetail.getSize());
    }

    public PageRequest detailOfSort(Sort sort) {
        return PageRequest.of(pageDetail.getPage(), pageDetail.getSize(), sort);
    }

    public Search getSearch() {
        return this.search;
    }
}
