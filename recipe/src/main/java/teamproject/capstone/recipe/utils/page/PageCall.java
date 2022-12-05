package teamproject.capstone.recipe.utils.page;

import lombok.*;

@Builder
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
