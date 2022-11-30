package teamproject.capstone.recipe.utils.page;

import lombok.*;
import org.springframework.data.domain.*;

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

    public abstract void makePageList(Pageable pageable);

    public abstract void setPage(Pageable pageable);
}
