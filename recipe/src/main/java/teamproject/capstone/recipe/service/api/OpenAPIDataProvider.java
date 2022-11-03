package teamproject.capstone.recipe.service.api;

import org.springframework.data.domain.Page;

public interface OpenAPIDataProvider {
    Page<Object[]> allAPIDataSources();
}
