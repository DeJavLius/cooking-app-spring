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
import teamproject.capstone.recipe.utils.page.Search;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAPIServiceImpl implements OpenAPIService, OpenAPIPageService, OpenAPISearchService, OpenAPIFavoriteService {
    private final OpenRecipeRepository openRecipeRepository;
    private final OpenRecipePageWithSearchRepository openRecipePageWithSearchRepository;

    @Override
    public OpenRecipe create(OpenRecipe openRecipe) {
        OpenRecipeEntity convertedDTO = OpenRecipeConverter.dtoToEntity(openRecipe);
        OpenRecipeEntity savedValueBack = openRecipeRepository.save(convertedDTO);
        return OpenRecipeConverter.entityToDto(savedValueBack);
    }

    @Override
    public List<OpenRecipe> createAll(List<OpenRecipe> openRecipes) {
        List<OpenRecipeEntity> convertedAllDTOs = openRecipes.stream().map(OpenRecipeConverter::dtoToEntity).collect(Collectors.toList());
        List<OpenRecipeEntity> savedValuesBack = openRecipeRepository.saveAll(convertedAllDTOs);
        return savedValuesBack.stream().map(OpenRecipeConverter::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(OpenRecipe openRecipe) {
        OpenRecipeEntity deleteData = OpenRecipeConverter.dtoToEntity(openRecipe);
        openRecipeRepository.delete(deleteData);
    }

    @Override
    public void deleteAll() {
        openRecipeRepository.deleteAll();
    }

    @Override
    public OpenRecipe findByRecipeSeq(Long recipeSeq) {
        Optional<OpenRecipeEntity> findOpenRecipe = openRecipeRepository.findByRcpSeq(recipeSeq);

        return findOpenRecipe.map(OpenRecipeConverter::entityToDto).orElse(OpenRecipe.builder().build());

    }

    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPIPageHandling(pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }


    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> searchOrAPIDataSources(List<Search> searchList, PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchOrPageHandling(searchList, pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }

    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(List<Search> searchList, PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchAndPageHandling(searchList, pageRequest);
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
        Optional<OpenRecipeEntity> findOpenRecipe = openRecipeRepository.findByRcpSeq(recipeSeq);
        findOpenRecipe.ifPresent(openRecipeEntity -> favorite.setRecipeSeq(openRecipeEntity.getRcpSeq()));
        return favorite;
    }

    @Override
    public List<OpenRecipe> rankFavoriteRecipe(List<Long> sequences) {
        List<OpenRecipe> resultOfRank = new ArrayList<>();

        for (Long seq : sequences) {
            resultOfRank.add(findByRecipeSeq(seq));
        }

        return resultOfRank;
    }
}
