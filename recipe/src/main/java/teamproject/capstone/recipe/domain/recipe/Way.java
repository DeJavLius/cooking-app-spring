package teamproject.capstone.recipe.domain.recipe;

import lombok.Data;

@Data
public class Way {
    private String value;

    public Way(String value) {
        this.value = value;
    }
}
