package teamproject.capstone.recipe.utils.api;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class APIPageHandler {
    public PageRequest choosePage(int page, int size) {
        APIPage pageMeta = APIPage.otherPage(page, size);
        return PageRequest.of(pageMeta.getPage(), pageMeta.getSize());
    }
}
