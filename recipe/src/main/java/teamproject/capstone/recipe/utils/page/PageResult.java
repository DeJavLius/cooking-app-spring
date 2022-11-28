package teamproject.capstone.recipe.utils.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class PageResult<DTO, EN> {
    private List<DTO> dtoList;

    private int totalPage;
    private int nowPage;
    private boolean firstPage, lastPage;

    public PageResult(Page<EN> result, Function<EN, DTO> fn) {
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    abstract void makePageList(Pageable pageable);

    abstract void setPage(Pageable pageable);
}
