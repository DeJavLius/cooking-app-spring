package teamproject.capstone.recipe.utils.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import teamproject.capstone.recipe.utils.api.APIPageHandler;
import teamproject.capstone.recipe.utils.page.PageDetailRequest;

@Component
public class PageHandlerImpl implements APIPageHandler {
    @Override
    public PageRequest choosePage(int page, int size) {
        PageDetailRequest pageMeta = PageDetailRequest.otherPage(page, size);
        return PageRequest.of(pageMeta.getPage(), pageMeta.getSize());
    }
}
