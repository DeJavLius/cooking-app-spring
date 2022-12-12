package teamproject.capstone.recipe.service.recipe;

import com.querydsl.core.Tuple;
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
public class RecipeAndSearchServiceImpl implements OpenRecipeService, OpenRecipePageWithSearchService, RecipeRecommendService, RecipeService {
    private final OpenRecipeRepository openRecipeRepository;
    private final OpenRecipePageWithSearchRepository openRecipePageWithSearchRepository;
    private final RecipeTupleRepository recipeTupleRepository;
    private final FavoriteRankRepository favoriteRankRepository;

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
    public List<OpenRecipe> findByRecipeSeqList(List<Long> seqList) {
        List<OpenRecipe> result = new ArrayList<>();
        List<Optional<OpenRecipeEntity>> recipeResult = seqList.stream().map(openRecipeRepository::findByRcpSeq).collect(Collectors.toList());

        for (Optional<OpenRecipeEntity> recipeEntity : recipeResult) {
            if (recipeEntity.isPresent()) {
                OpenRecipe openRecipe = OpenRecipeConverter.entityToDto(recipeEntity.get());
                result.add(openRecipe);
            }
        }
        return result;
    }

    @Override
    public List<Recommend> findRecommendRecipe(Search search) {
        List<Object[]> objects = recipeTupleRepository.sameRecommendRecipe(search);
        return objects.stream().map(objs -> Recommend.builder().id((Long) objs[0]).image((String) objs[1]).name((String) objs[2]).build()).collect(Collectors.toList());
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
    public RecipePageResult<Favorite, Object[]> searchTuplePageWithSortRecipes(Search search, PageRequest pageRequest) {
        Page<Object[]> recipeDetails = openRecipePageWithSearchRepository.recipeSearchAndPageSeparateHandling(search, pageRequest);
        Function<Object[], Favorite> fn = (entity -> Favorite.builder().recipeId((Long) entity[0]).recipeMainImage((String) entity[1]).recipePart((String) entity[2]).recipeName((String) entity[3]).count((Long) entity[4]).build());
        return new RecipePageResult<>(recipeDetails, fn);
    }

    @Override
    public List<OpenRecipe> mostAndroidRecipe() {
        List<OpenRecipe> result = new ArrayList<>();
        List<Object[]> rankFavorites = favoriteRankRepository.findWithRankFavoriteRecipe();
        if (!rankFavorites.isEmpty()) {
            result = rankFavorites.stream().map(entity -> OpenRecipeConverter.entityToDto((OpenRecipeEntity) entity[1])).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<Way> recipeWayValueFound() {
        return recipeTupleRepository.recipeWayExtract().stream().map(Way::new).collect(Collectors.toList());
    }

    @Override
    public List<Part> recipePartValueFound() {
        return recipeTupleRepository.recipePartExtract().stream().map(Part::new).collect(Collectors.toList());
    }
}
