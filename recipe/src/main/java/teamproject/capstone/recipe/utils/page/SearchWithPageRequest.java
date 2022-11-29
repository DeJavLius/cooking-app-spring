package teamproject.capstone.recipe.utils.page;

import org.springframework.data.domain.*;

public class SearchWithPageRequest {
    private final PageDetailRequest pageDetail;
    private final SearchWrapper searchType;

    private SearchWithPageRequest(SearchWrapper searchType, PageDetailRequest pageDetail) {
        this.searchType = searchType;
        this.pageDetail = pageDetail;
    }

    public static SearchWithPageRequest searchPageRequest(SearchWrapper searchType, int page, int size) {
        PageDetailRequest pageDetail = PageDetailRequest.otherPage(page, size);
        return new SearchWithPageRequest(searchType, pageDetail);
    }

    public PageRequest detailOf() {
        return PageRequest.of(pageDetail.getPage(), pageDetail.getSize());
    }

    public PageRequest detailOfSort(Sort sort) {
        return PageRequest.of(pageDetail.getPage(), pageDetail.getSize(), sort);
    }

    public SearchWrapper getSearchType() {
        return this.searchType;
    }
}
