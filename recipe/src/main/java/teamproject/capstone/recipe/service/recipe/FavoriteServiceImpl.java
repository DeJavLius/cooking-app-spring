package teamproject.capstone.recipe.service.recipe;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.repository.recipe.FavoriteRankRepository;
import teamproject.capstone.recipe.repository.recipe.FavoriteRepository;
import teamproject.capstone.recipe.repository.recipe.FavoriteSimpleRepository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService, FavoriteRankService {
    private final FavoriteSimpleRepository favoriteSimpleRepository;
    private final FavoriteRankRepository favoriteRankRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public List<Favorite> createAll(List<Favorite> favorites) {
        List<Favorite> savedEntities = new ArrayList<>();
        List<FavoriteEntity> createEntities = favorites.stream().map(this::dtoToEntity).collect(Collectors.toList());

        for (FavoriteEntity entity : createEntities) {
            Favorite favorite = entityToDto(favoriteSimpleRepository.save(entity));
            savedEntities.add(favorite);
        }

        return savedEntities;
    }

    @Override
    public List<Favorite> createAll(String email, List<OpenRecipe> recipeList) {
        List<Favorite> savedEntities = new ArrayList<>();

        for (OpenRecipe or : recipeList) {
            Favorite f = Favorite.builder()
                    .recipeId(or.getId())
                    .recipeSeq(or.getRcpSeq())
                    .userEmail(email)
                    .build();
            FavoriteEntity favoriteEntity = dtoToEntity(f);
            Favorite favorite = entityToDto(favoriteSimpleRepository.save(favoriteEntity));
            savedEntities.add(favorite);
        }

        return savedEntities;
    }

    @Override
    public void delete(Favorite favorite) {
        FavoriteEntity deleteFavorite = dtoToEntity(favorite);
        favoriteSimpleRepository.delete(deleteFavorite);
    }

    @Override
    public Favorite create(Favorite favorite) {
        FavoriteEntity favoriteEntity = dtoToEntity(favorite);
        if (!isExist(favorite.getRecipeId(), favorite.getUserEmail())) {
            FavoriteEntity savedFavoriteEntity = favoriteSimpleRepository.save(favoriteEntity);
            return entityToDto(savedFavoriteEntity);
        }
        return Favorite.builder().build();
    }

    private boolean isExist(Long recipeSeq, String email) {
        return favoriteRepository.findFavoriteByRecipeSeqAndEmail(recipeSeq, email) != null;
    }

    @Transactional
    @Override
    public void deleteByEmail(String email) {
        favoriteSimpleRepository.deleteByUserEmail(email);
    }

    @Transactional
    @Override
    public void deleteAll() {
        favoriteSimpleRepository.deleteAll();
    }

    @Override
    public Favorite findRecipe(Long recipeSeq, String email) {
        Favorite favorite = Favorite.builder().id(0L).build();

        Object[] foundRawValue = favoriteRepository.findFavoriteByRecipeSeqAndEmail(recipeSeq, email);
        if (foundRawValue != null) {
            FavoriteEntity favoriteEntity = (FavoriteEntity) foundRawValue[0];
            OpenRecipeEntity openRecipeEntity = (OpenRecipeEntity) foundRawValue[1];
            favorite = entityToDto(favoriteEntity, openRecipeEntity);
        }
        return favorite;
    }

    @Override
    public List<Favorite> findAll() {
        List<Object[]> allFavorite = favoriteRepository.findAllFavorite();
        return valueNotFoundCheck(allFavorite);
    }

    @Override
    public List<Favorite> findByEmail(String email) {
        List<Object[]> allFavorite = favoriteRepository.findFavoriteByEmail(email);
        return valueNotFoundCheck(allFavorite);
    }

    @Override
    public List<Favorite> findBySeq(Long recipeSeq) {
        List<Object[]> allFavorite = favoriteRepository.findFavoriteByRecipeSeq(recipeSeq);
        return valueNotFoundCheck(allFavorite);
    }

    @Override
    public List<Favorite> mostFavoriteRecipe() {
        List<Object[]> allFavorite = favoriteRankRepository.findWithRankFavoriteRecipe();
        return valueNotFoundCheck(allFavorite);
    }

    @Override
    public List<Favorite> usersFavoriteRecipe(String email) {
        List<Object[]> allFavorite = favoriteRankRepository.findRankFavoriteRecipeByEmail(email);
        return valueNotFoundCheck(allFavorite);
    }

    @Override
    public List<Long> usersFavoriteOnlySeq(String email) {
        return favoriteRankRepository.findRankFavoriteRecipeByEmailOnlySeq(email);
    }

    private List<Favorite> valueNotFoundCheck(List<Object[]> found) {
        List<Favorite> favoriteList = new ArrayList<>();

        if (found != null) {
            for (Object[] values : found) {
                Favorite f = entityToDto((FavoriteEntity) values[0], (OpenRecipeEntity) values[1]);
                f.setCount((Long) values[2]);
                favoriteList.add(f);
            }
        } else {
            favoriteList.add(Favorite.builder().id(0L).build());
        }
        return favoriteList;
    }
}
