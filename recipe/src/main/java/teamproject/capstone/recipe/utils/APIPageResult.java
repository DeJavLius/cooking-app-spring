package teamproject.capstone.recipe.utils;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class APIPageResult<DTO, EN> {
    private List<DTO> dtoList;

    private int totalPage;
    private int currentPage;
    private int startPage = 1;
    private int endPage;
    private boolean firstPage, lastPage;
    private List<Integer> pageList;

    /*
    * API page rule
    * start page : 1
    * end page : total count / page size : (in page element count) + if (total count % page size : (in page element count) > 0) + 1
    *   ex) 1061 / 10 == 106 + 1 == 107 page
    * one page : total page size set = n, n is set by param
    * */

    public APIPageResult(Page<EN> result, Function<EN, DTO> fn) {
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        setCurrentPage(pageable);
        firstPage = (currentPage == startPage);
        lastPage = (currentPage == totalPage);
        endPage = totalPage;

        pageList = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
    }

    private void setCurrentPage(Pageable pageable) {
        this.currentPage = pageable.getPageNumber() + 1;
    }
}
