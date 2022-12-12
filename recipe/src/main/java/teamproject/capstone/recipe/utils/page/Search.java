package teamproject.capstone.recipe.utils.page;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Search {
    @Builder.Default
    private String name = "";
    @Builder.Default
    private String detail = "";
    @Builder.Default
    private String part = "";
    @Builder.Default
    private Long seq = 0L;
    @Builder.Default
    private String way = "";

    public Search() {
        this.name = "";
        this.detail = "";
        this.part = "";
        this.way = "";
        this.seq = 0L;
    }
}
