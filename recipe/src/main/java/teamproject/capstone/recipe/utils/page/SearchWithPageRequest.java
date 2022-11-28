package teamproject.capstone.recipe.utils.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchWithPageRequest {
    private SearchWrapper searchType;
    private PageDetailRequest pageDetail;
}
