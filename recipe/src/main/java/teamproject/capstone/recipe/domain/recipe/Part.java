package teamproject.capstone.recipe.domain.recipe;

import lombok.Data;

@Data
public class Part {
    private String value;

    public Part(String value) {
        this.value = value;
    }
}
