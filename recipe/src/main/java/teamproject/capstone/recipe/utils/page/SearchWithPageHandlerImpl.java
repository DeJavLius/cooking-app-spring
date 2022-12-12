package teamproject.capstone.recipe.utils.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import teamproject.capstone.recipe.domain.recipe.Favorite;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchWithPageHandlerImpl<T> implements SearchWithPageHandler<T> {
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

    @Override
    public List<List<T>> pageRowRank(List<T> list) {
        List<List<T>> rowList = new ArrayList<>();

        int start = 0;
        int subIn = 4;
        int midIndex = start + subIn;
        int recipesSize = list.size();

        while (true) {
            if (midIndex >= recipesSize) {
                midIndex = recipesSize;
                rowList.add(list.subList(start, midIndex));
                break;
            } else {
                rowList.add(list.subList(start, midIndex));
                start += subIn;
                midIndex += subIn;
            }
        }
        return rowList;
    }
}
