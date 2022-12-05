package teamproject.capstone.recipe.service.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import teamproject.capstone.recipe.domain.recipe.*;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.repository.recipe.*;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.page.*;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeAndSearchServiceImpl implements OpenRecipeService, OpenRecipePageWithSearchService, OpenRecipeFavoriteService, RecipeService {
    private final OpenRecipeRepository openRecipeRepository;
    private final OpenRecipePageWithSearchRepository openRecipePageWithSearchRepository;
    private final RecipeTupleRepository recipeTupleRepository;

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
    public OpenRecipe findRecipe(Long id) {
        Optional<OpenRecipeEntity> find = openRecipeRepository.findById(id);

        return find.map(OpenRecipeConverter::entityToDto).orElse(OpenRecipe.builder().build());
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
    public APIPageResult<OpenRecipe, OpenRecipeEntity> searchOrAPIDataSources(Search search, PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchOrPageHandling(search, pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }

    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(Search search, PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchAndPageHandling(search, pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }

    @Override
    public RecipePageResult<OpenRecipe, OpenRecipeEntity> searchPageWithSortRecipes(Search search, PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.recipeSearchAndPageHandling(search, pageRequest);
        return new RecipePageResult<>(openRecipeEntities, function);
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

    @Override
    public List<String> recipeWayValueFound() {
        return recipeTupleRepository.recipeWayExtract();
    }

    @Override
    public List<String> recipePartValueFound() {
        return recipeTupleRepository.recipePartExtract();
    }
}
