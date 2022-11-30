package teamproject.capstone.recipe.utils.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageCall {
    private int page;
    private int size;
    private String order;

    public PageCall() {
        this.page = 1;
        this.size = 28;
        this.order = "";
    }
}
