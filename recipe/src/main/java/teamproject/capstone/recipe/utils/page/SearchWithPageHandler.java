package teamproject.capstone.recipe.utils.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public interface SearchWithPageHandler {
    PageRequest choosePage(int page, int size);
    SearchWithPageRequest choosePageWithSearch(SearchWrapper searchWrapper, int page, int size);
    PageRequest searchPageWithSort(SearchWithPageRequest searchWithPageRequest, Sort sort);
}
