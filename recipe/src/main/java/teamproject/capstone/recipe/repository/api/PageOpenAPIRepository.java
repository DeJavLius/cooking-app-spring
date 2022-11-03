package teamproject.capstone.recipe.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageOpenAPIRepository {
    Page<Object[]> openAPIPageHandling(Pageable pageable);
}
