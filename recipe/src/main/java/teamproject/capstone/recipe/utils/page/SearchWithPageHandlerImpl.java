package teamproject.capstone.recipe.utils.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SearchWithPageHandlerImpl implements SearchWithPageHandler {
    @Override
    public PageRequest choosePage(int page, int size) {
        PageDetailRequest pageMeta = PageDetailRequest.otherPage(page, size);
        return PageRequest.of(pageMeta.getPage(), pageMeta.getSize());
    }

    @Override
    public SearchWithPageRequest choosePageWithSearch(Search value, int page, int size) {
        Search search = new Search();
        if (value != null) {
            search = Search.builder().name(value.getName()).detail(value.getDetail()).part(value.getPart()).seq(value.getSeq()).way(value.getWay()).build();
        }
        return SearchWithPageRequest.searchPageRequest(search, PageDetailRequest.otherPage(page, size));
    }

    @Override
    public PageRequest searchPageWithSort(SearchWithPageRequest searchWithPageRequest, Sort sort) {
        return searchWithPageRequest.detailOfSort(sort);
    }
}
