package teamproject.capstone.recipe.utils.api;

import org.springframework.data.domain.PageRequest;

public interface APIPageHandler {
    PageRequest choosePage(int page, int size);
}
