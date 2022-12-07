package teamproject.capstone.recipe.service.recipe;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.repository.recipe.FavoriteRepository;
import teamproject.capstone.recipe.repository.recipe.FavoriteSimpleRepository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService, FavoriteRankService {
    private final FavoriteSimpleRepository favoriteSimpleRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public List<Favorite> createAll(List<Favorite> favorites) {
        List<Favorite> savedEntities = new ArrayList<>();
        List<FavoriteEntity> createEntities = favorites.stream().map(this::dtoToEntity).collect(Collectors.toList());

        for (FavoriteEntity entity : createEntities) {
            Favorite favorite = create(entityToDto(entity));
            savedEntities.add(favorite);
        }

        return savedEntities;
    }

    @Override
    public Favorite create(Favorite favorite) {
        if (!isPresent(favorite.getRecipeSeq(), favorite.getUserEmail())) {
            FavoriteEntity favoriteEntity = dtoToEntity(favorite);
            FavoriteEntity savedFavoriteEntity = favoriteSimpleRepository.save(favoriteEntity);
            return entityToDto(savedFavoriteEntity);
        }
        return Favorite.builder().recipeId(0L).recipeSeq(0L).userEmail("").build();
    }

    private boolean isPresent(Long recipeSeq, String userEmail) {
        Object[] find = favoriteRepository.findFavoriteByRecipeSeqAndEmail(recipeSeq, userEmail);
        return find != null;
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
    public Favorite findRecipe(long recipeSeq, String email) {
        return null;
    }

    @Override
    public List<Favorite> findAll() {
        return favoriteSimpleRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Favorite> findByEmail(String userEmail) {
        return null;
    }

    @Override
    public List<Favorite> findBySeq(long recipeSeq) {
        return null;
    }

    @Override
    public boolean isFavoriteNotExist(Favorite favorite) {
        return false;
    }

    @Override
    public List<Favorite> usersFavoriteRecipe(String email) {
        return null;
    }
}
