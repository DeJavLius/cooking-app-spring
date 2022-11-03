package teamproject.capstone.recipe.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.repository.api.OpenAPIRepository;
import teamproject.capstone.recipe.repository.api.PageOpenAPIRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAPIServiceImpl implements OpenAPIService, OpenAPIDataProvider {
    private final OpenAPIRepository openAPIRepository;
    private final PageOpenAPIRepository pageOpenAPIRepository;

    @Override
    public Page<Object[]> allAPIDataSources() {
        return null;
    }

    @Override
    public OpenRecipe create(OpenRecipe openRecipe) {
        return null;
    }

    @Override
    public List<OpenRecipe> createAll(List<OpenRecipe> openRecipes) {
        return null;
    }
}
