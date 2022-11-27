package teamproject.capstone.recipe.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.repository.api.*;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.api.APISearch;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAPIServiceImpl implements OpenAPIService, OpenAPIPageService, OpenAPISearchService, OpenAPIFavoriteService {
    private final OpenAPIRepository openAPIRepository;
    private final OpenAPIPageRepository openAPIPageRepository;
    private final OpenAPISearchRepository openAPISearchRepository;

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

    @Override
    public OpenRecipe findByRecipeSeq(Long recipeSeq) {
        Optional<OpenRecipeEntity> findOpenRecipe = openAPIRepository.findByRcpSeq(recipeSeq);

        return findOpenRecipe.map(OpenRecipeConverter::entityToDto).orElse(OpenRecipe.builder().build());

    }

    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openAPIPageRepository.openAPIPageHandling(pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }


    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> searchOrAPIDataSources(List<APISearch> apiSearchList, PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openAPISearchRepository.openAPISearchOrPageHandling(apiSearchList, pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }

    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(List<APISearch> apiSearchList, PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openAPISearchRepository.openAPISearchAndPageHandling(apiSearchList, pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }

    @Override
    public List<FavoriteRecipe> provideFavorites(String email, List<Long> recipeSeqList) {
        List<FavoriteRecipe> findResult = new ArrayList<>();
        for (Long seq : recipeSeqList) {
            FavoriteRecipe provide = provideFavorite(email, seq);
            findResult.add(provide);
        }
        return findResult;
    }

    @Override
    public FavoriteRecipe provideFavorite(String email, Long recipeSeq) {
        FavoriteRecipe favorite = FavoriteRecipe.builder().userEmail(email).build();
        Optional<OpenRecipeEntity> findOpenRecipe = openAPIRepository.findByRcpSeq(recipeSeq);
        findOpenRecipe.ifPresent(openRecipeEntity -> favorite.setRecipeSeq(openRecipeEntity.getRcpSeq()));
        return favorite;
    }
}
