package teamproject.capstone.recipe.utils.page;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.capstone.recipe.utils.page.PageResult;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class RecipePageResult<DTO, EN> extends PageResult<DTO, EN> {
    /*
     * Recipe page rule
     * start page : 1
     * end page : total count / page size : (in page element count) + if (total count % page size : (in page element count) > 0) + 1
     *   ex) 1061 / 10 == 106 + 1 == 107 page
     * one page : total page size set = n, n is set by param
     * */

    private int sizeOfPage;
    private int numberOfPage;
    private int startPage, endPage;
    private boolean prevPage, nextPage;

    private List<Integer> pageList;

    public RecipePageResult(Page<EN> result, Function<EN, DTO> fn) {
        super(result, fn);
    }

    @Override
    public void makePageList(Pageable pageable) {
        setPage(pageable);
        boolean isLast = super.getNowPage() == super.getTotalPage();
        super.setLastPage(isLast);

        double pageDouble = (double) sizeOfPage;
        int tempEnd = (int)(Math.ceil(super.getNowPage()/pageDouble)) * sizeOfPage;

        startPage = tempEnd - (sizeOfPage - 1);
        endPage = Math.min(super.getTotalPage(), tempEnd);
        prevPage = startPage > 1;
        nextPage = super.getTotalPage() > tempEnd;

        super.setFirstPage(prevPage);
        super.setLastPage(nextPage);

        pageList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
    }

    @Override
    public void setPage(Pageable pageable) {
        super.setNowPage(pageable.getPageNumber() + 1);

        this.numberOfPage = 10;
        this.sizeOfPage = this.numberOfPage;
    }
}
