package teamproject.capstone.recipe.utils.page;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
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
}
