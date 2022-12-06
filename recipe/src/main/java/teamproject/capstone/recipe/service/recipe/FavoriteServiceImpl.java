package teamproject.capstone.recipe.service.recipe;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.utils.api.json.FavoriteRecipe;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.repository.recipe.FavoriteRankRepository;
import teamproject.capstone.recipe.repository.recipe.FavoriteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService, FavoriteRankService {
    private final FavoriteRepository favoriteRepository;
    private final FavoriteRankRepository favoriteRankRepository;

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
            FavoriteEntity savedFavoriteEntity = favoriteRepository.save(favoriteEntity);
            return entityToDto(savedFavoriteEntity);
        }
        return Favorite.builder().recipeSeq(null).userEmail(favorite.getUserEmail()).build();
    }

    private boolean isPresent(Long seq, String email) {
        Optional<FavoriteEntity> byRecipeSeqAndUserEmail = favoriteRepository.findByRecipeSeqAndUserEmail(seq, email);
        return byRecipeSeqAndUserEmail.isPresent();
    }

    @Transactional
    @Override
    public void delete(String email, Long recipeSeq) {
        favoriteRepository.deleteByUserEmailAndRecipeSeq(email, recipeSeq);
    }

    @Transactional
    @Override
    public void deleteByEmail(String email) {
        favoriteRepository.deleteByUserEmail(email);
    }

    @Transactional
    @Override
    public void deleteAll() {
        favoriteRepository.deleteAll();
    }

    @Override
    public Favorite findRecipe(long recipeSeq, String email) {
        Optional<FavoriteEntity> findEntityBySeqAndEmail = favoriteRepository.findByRecipeSeqAndUserEmail(recipeSeq, email);
        FavoriteEntity favoriteEntity = findEntityBySeqAndEmail.orElse(FavoriteEntity.builder().build());
        return entityToDto(favoriteEntity);
    }

    @Override
    public List<Favorite> findAll() {
        return favoriteRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Favorite> findByEmail(String email) {
        Optional<List<FavoriteEntity>> findEntities = favoriteRepository.findByUserEmail(email);
        List<FavoriteEntity> favoriteEntities = findEntities.orElse(new ArrayList<>());

        return entitiesToDto(favoriteEntities);
    }

    @Override
    public List<Favorite> findBySeq(long recipeSeq) {
        Optional<List<FavoriteEntity>> findEntities = favoriteRepository.findByRecipeSeq(recipeSeq);
        List<FavoriteEntity> favoriteEntities = findEntities.orElse(new ArrayList<>());

        return entitiesToDto(favoriteEntities);
    }

    private List<Favorite> entitiesToDto(List<FavoriteEntity> entities) {
        List<Favorite> findDomains = new ArrayList<>();
        for (FavoriteEntity entity : entities) {
            Favorite dto = entityToDto(entity);

            findDomains.add(dto);
        }

        return findDomains;
    }

    @Override
    public List<Long> mostFavoriteRankRecipe() {
        List<Tuple> favoriteRank = favoriteRankRepository.findWithRankFavoriteRecipe();
        List<Long> resultSeq = new ArrayList<>();
        for (Tuple tuple : favoriteRank) {
            resultSeq.add(tuple.get(0, Long.class));
        }
        return resultSeq;
    }

    @Override
    public List<Long> allFavoriteRecipe(String email) {
        return favoriteRankRepository.findAllFavoriteRecipe(email);
    }

    @Override
    public boolean isFavoriteNotExist(Favorite favorite) {
        Optional<FavoriteEntity> favoriteRecipeFind = favoriteRepository.findByRecipeSeqAndUserEmail(favorite.getRecipeSeq(), favorite.getUserEmail());
        return favoriteRecipeFind.isEmpty();
    }
}
