package teamproject.capstone.recipe.utils.api;

import lombok.*;
import org.springframework.data.domain.*;
import teamproject.capstone.recipe.utils.page.PageResult;

import java.util.function.Function;

@Getter
@Setter
@ToString
public class APIPageResult<DTO, EN> extends PageResult<DTO, EN> {
    /*
    * API page rule
    * start page : 1
    * end page : total count / page size : (in page element count) + if (total count % page size : (in page element count) > 0) + 1
    *   ex) 1061 / 10 == 106 + 1 == 107 page
    * one page : total page size set = n, n is set by param
    * */

    public APIPageResult(Page<EN> result, Function<EN, DTO> fn) {
        super(result, fn);
    }

    @Override
    public void makePageList(Pageable pageable) {
        setPage(pageable);
        boolean isLast = super.getNowPage() == super.getTotalPage();
        super.setLastPage(isLast);
    }

    @Override
    public void setPage(Pageable pageable) {
        super.setNowPage(pageable.getPageNumber() + 1);
    }
}
