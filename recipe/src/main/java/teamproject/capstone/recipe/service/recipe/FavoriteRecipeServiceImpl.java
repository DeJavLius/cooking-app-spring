package teamproject.capstone.recipe.service.recipe;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;
import teamproject.capstone.recipe.entity.recipe.FavoriteRecipeEntity;
import teamproject.capstone.recipe.repository.recipe.FavoriteRecipeRankRepository;
import teamproject.capstone.recipe.repository.recipe.FavoriteRecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class FavoriteRecipeServiceImpl implements FavoriteRecipeService, FavoriteRecipeRankService {
    private final FavoriteRecipeRepository favoriteRecipeRepository;
    private final FavoriteRecipeRankRepository favoriteRecipeRankRepository;

    @Override
    public List<FavoriteRecipe> createAll(List<FavoriteRecipe> favoriteRecipes) {
        List<FavoriteRecipe> savedEntities = new ArrayList<>();
        List<FavoriteRecipeEntity> createEntities = favoriteRecipes.stream().map(this::dtoToEntity).collect(Collectors.toList());

        for (FavoriteRecipeEntity entity : createEntities) {
            FavoriteRecipe favoriteRecipe = create(entityToDto(entity));
            savedEntities.add(favoriteRecipe);
        }

        return savedEntities;
    }

    @Override
    public FavoriteRecipe create(FavoriteRecipe favoriteRecipe) {
        if (!isPresent(favoriteRecipe.getRecipeSeq(), favoriteRecipe.getUserEmail())) {
            FavoriteRecipeEntity favoriteRecipeEntity = dtoToEntity(favoriteRecipe);
            FavoriteRecipeEntity savedFavoriteRecipeEntity = favoriteRecipeRepository.save(favoriteRecipeEntity);
            return entityToDto(savedFavoriteRecipeEntity);
        }
        return FavoriteRecipe.builder().recipeSeq(null).userEmail(favoriteRecipe.getUserEmail()).build();
    }

    private boolean isPresent(Long seq, String email) {
        Optional<FavoriteRecipeEntity> byRecipeSeqAndUserEmail = favoriteRecipeRepository.findByRecipeSeqAndUserEmail(seq, email);
        return byRecipeSeqAndUserEmail.isPresent();
    }

    @Transactional
    @Override
    public void delete(String email, Long recipeSeq) {
        favoriteRecipeRepository.deleteByUserEmailAndRecipeSeq(email, recipeSeq);
    }

    @Transactional
    @Override
    public void deleteByEmail(String email) {
        favoriteRecipeRepository.deleteByUserEmail(email);
    }

    @Transactional
    @Override
    public void deleteAll() {
        favoriteRecipeRepository.deleteAll();
    }

    @Override
    public FavoriteRecipe findRecipe(long recipeSeq, String email) {
        Optional<FavoriteRecipeEntity> findEntityBySeqAndEmail = favoriteRecipeRepository.findByRecipeSeqAndUserEmail(recipeSeq, email);
        FavoriteRecipeEntity favoriteRecipeEntity = findEntityBySeqAndEmail.orElse(FavoriteRecipeEntity.builder().build());
        return entityToDto(favoriteRecipeEntity);
    }

    @Override
    public List<FavoriteRecipe> findAll() {
        return favoriteRecipeRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<FavoriteRecipe> findByEmail(String email) {
        Optional<List<FavoriteRecipeEntity>> findEntities = favoriteRecipeRepository.findByUserEmail(email);
        List<FavoriteRecipeEntity> favoriteRecipeEntities = findEntities.orElse(new ArrayList<>());

        return entitiesToDto(favoriteRecipeEntities);
    }

    @Override
    public List<FavoriteRecipe> findBySeq(long recipeSeq) {
        Optional<List<FavoriteRecipeEntity>> findEntities = favoriteRecipeRepository.findByRecipeSeq(recipeSeq);
        List<FavoriteRecipeEntity> favoriteRecipeEntities = findEntities.orElse(new ArrayList<>());

        return entitiesToDto(favoriteRecipeEntities);
    }

    private List<FavoriteRecipe> entitiesToDto(List<FavoriteRecipeEntity> entities) {
        List<FavoriteRecipe> findDomains = new ArrayList<>();
        for (FavoriteRecipeEntity entity : entities) {
            FavoriteRecipe dto = entityToDto(entity);

            findDomains.add(dto);
        }

        return findDomains;
    }

    @Override
    public List<Long> mostFavoriteRankRecipe() {
        List<Tuple> favoriteRecipeRank = favoriteRecipeRankRepository.findWithRankFavoriteRecipe();
        List<Long> resultSeq = new ArrayList<>();
        for (Tuple tuple : favoriteRecipeRank) {
            resultSeq.add(tuple.get(0, Long.class));
        }
        return resultSeq;
    }
}
