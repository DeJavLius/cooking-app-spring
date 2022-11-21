package teamproject.capstone.recipe.service.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;
import teamproject.capstone.recipe.entity.recipe.FavoriteRecipeEntity;
import teamproject.capstone.recipe.repository.recipe.FavoriteRecipeRepository;
import teamproject.capstone.recipe.utils.converter.FavoriteRecipeConverter;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FavoriteRecipeServiceImpl implements FavoriteRecipeService {
    private final FavoriteRecipeRepository favoriteRecipeRepository;

    @Override
    public FavoriteRecipe create(FavoriteRecipe favoriteRecipe) {
        FavoriteRecipeEntity favoriteRecipeEntity = FavoriteRecipeConverter.dtoToEntity(favoriteRecipe);
        FavoriteRecipeEntity savedFavoriteRecipeEntity = favoriteRecipeRepository.save(favoriteRecipeEntity);
        return FavoriteRecipeConverter.entityToDto(savedFavoriteRecipeEntity);
    }

    @Override
    public List<FavoriteRecipe> createAll(List<FavoriteRecipe> favoriteRecipes) {
        return null;
    }

    @Override
    public void delete(FavoriteRecipe favoriteRecipe) {

    }

    @Override
    public void deleteByEmail(String email) {

    }

    @Override
    public void deleteAll(List<FavoriteRecipe> favoriteRecipes) {

    }
}
