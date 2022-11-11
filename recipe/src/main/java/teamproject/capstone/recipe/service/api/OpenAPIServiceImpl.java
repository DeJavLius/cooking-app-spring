package teamproject.capstone.recipe.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.repository.api.OpenAPIRepository;
import teamproject.capstone.recipe.repository.api.OpenAPIPageRepository;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAPIServiceImpl implements OpenAPIService, OpenAPIPageService {
    private final OpenAPIRepository openAPIRepository;
    private final OpenAPIPageRepository openAPIPageRepository;

    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openAPIPageRepository.openAPIPageHandling(pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }

    @Override
    public OpenRecipe create(OpenRecipe openRecipe) {
        OpenRecipeEntity convertedDTO = OpenRecipeConverter.dtoToEntity(openRecipe);
        OpenRecipeEntity savedValueBack = openAPIRepository.save(convertedDTO);
        return OpenRecipeConverter.entityToDto(savedValueBack);
    }

    @Override
    public List<OpenRecipe> createAll(List<OpenRecipe> openRecipes) {
        List<OpenRecipeEntity> convertedAllDTOs = openRecipes.stream().map(OpenRecipeConverter::dtoToEntity).collect(Collectors.toList());
        List<OpenRecipeEntity> savedValuesBack = openAPIRepository.saveAll(convertedAllDTOs);
        return savedValuesBack.stream().map(OpenRecipeConverter::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(OpenRecipe openRecipe) {
        OpenRecipeEntity deleteData = OpenRecipeConverter.dtoToEntity(openRecipe);
        openAPIRepository.delete(deleteData);
    }

    @Override
    public void deleteAll() {
        openAPIRepository.deleteAll();
    }


}
