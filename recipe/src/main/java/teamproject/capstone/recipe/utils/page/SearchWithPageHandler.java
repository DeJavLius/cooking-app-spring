package teamproject.capstone.recipe.utils.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SearchWithPageHandler<T> {
    PageRequest choosePage(int page, int size);
    SearchWithPageRequest choosePageWithSearch(Search search, int page, int size);
    PageRequest searchPageWithSort(SearchWithPageRequest searchWithPageRequest, Sort sort);

    List<List<T>> pageRowRank(List<T> list);
}
