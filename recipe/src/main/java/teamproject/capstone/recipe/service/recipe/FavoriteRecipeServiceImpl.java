package teamproject.capstone.recipe.service.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;
import teamproject.capstone.recipe.entity.recipe.FavoriteRecipeEntity;
import teamproject.capstone.recipe.repository.recipe.FavoriteRecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class FavoriteRecipeServiceImpl implements FavoriteRecipeService {
    private final FavoriteRecipeRepository favoriteRecipeRepository;

    @Override
    public FavoriteRecipe create(FavoriteRecipe favoriteRecipe) {
        FavoriteRecipeEntity favoriteRecipeEntity = dtoToEntity(favoriteRecipe);
        FavoriteRecipeEntity savedFavoriteRecipeEntity = favoriteRecipeRepository.save(favoriteRecipeEntity);
        return entityToDto(savedFavoriteRecipeEntity);
    }

    @Override
    public List<FavoriteRecipe> createAll(List<FavoriteRecipe> favoriteRecipes) {
        List<FavoriteRecipeEntity> createEntities = favoriteRecipes.stream().map(this::dtoToEntity).collect(Collectors.toList());
        List<FavoriteRecipeEntity> savedEntities = favoriteRecipeRepository.saveAll(createEntities);
        return savedEntities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(FavoriteRecipe favoriteRecipe) {
        FavoriteRecipeEntity deleteEntity = dtoToEntity(favoriteRecipe);
        favoriteRecipeRepository.delete(deleteEntity);
    }

    @Override
    public void deleteByEmail(String email) {
        favoriteRecipeRepository.deleteByUserEmail(email);
    }

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
}
