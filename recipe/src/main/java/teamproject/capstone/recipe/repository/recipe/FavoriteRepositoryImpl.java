package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.entity.recipe.QFavoriteEntity;
import teamproject.capstone.recipe.entity.recipe.QOpenRecipeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FavoriteRepositoryImpl extends QuerydslRepositorySupport implements FavoriteRepository, FavoriteRankRepository {
    private final QFavoriteEntity favoriteEntity = QFavoriteEntity.favoriteEntity;
    private final QOpenRecipeEntity openRecipeEntity = QOpenRecipeEntity.openRecipeEntity;

    public FavoriteRepositoryImpl() {
        super(FavoriteEntity.class);
    }

    @Override
    public List<Object[]> findWithRankFavoriteRecipe() {
        JPQLQuery<Tuple> selectFavoriteRecipe = selectFavoriteRecipeWithRank(from(favoriteEntity));
        return orderFavoriteRecipe(selectFavoriteRecipe).fetch().stream().map(Tuple::toArray).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> findRankFavoriteRecipeByEmail(String email) {
        JPQLQuery<Tuple> selectFavoriteRecipe = selectFavoriteRecipeWithRank(from(favoriteEntity));
        return whereFavoriteRecipe(selectFavoriteRecipe, email).fetch().stream().map(Tuple::toArray).collect(Collectors.toList());
    }

    @Override
    public List<Long> findRankFavoriteRecipeByEmailOnlySeq(String email) {
        JPQLQuery<Long> selectFavoriteRecipeId = selectFavoriteRecipeId(from(favoriteEntity));
        return whereFavoriteRecipeId(selectFavoriteRecipeId, email).fetch();
    }

    @Override
    public List<Object[]> findFavoriteByRecipeSeq(Long recipeSeq) {
        JPQLQuery<Tuple> selectFavoriteRecipe = selectFavoriteRecipe(from(favoriteEntity));
        selectFavoriteRecipe.where(favoriteEntity.recipe.rcpSeq.eq(recipeSeq));
        return selectFavoriteRecipe.fetch().stream().map(Tuple::toArray).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> findFavoriteByEmail(String email) {
        JPQLQuery<Tuple> selectFavoriteRecipe = selectFavoriteRecipe(from(favoriteEntity));
        selectFavoriteRecipe.where(favoriteEntity.userEmail.eq(email));
        return selectFavoriteRecipe.fetch().stream().map(Tuple::toArray).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> findAllFavorite() {
        JPQLQuery<Tuple> selectFavoriteRecipe = selectFavoriteRecipe(from(favoriteEntity));
        return selectFavoriteRecipe.fetch().stream().map(Tuple::toArray).collect(Collectors.toList());
    }

    @Override
    public Object[] findFavoriteByRecipeSeqAndEmail(Long recipeSeq, String email) {
        JPQLQuery<Tuple> selectFavoriteRecipe = selectFavoriteRecipe(from(favoriteEntity));
        selectFavoriteRecipe.where(favoriteEntity.recipe.rcpSeq.eq(recipeSeq).and(favoriteEntity.userEmail.eq(email)));
        Tuple tuple = selectFavoriteRecipe.fetchFirst();

        if (tuple != null) {
            return tuple.toArray();
        }
        return null;
    }

    private JPQLQuery<Tuple> selectFavoriteRecipeWithRank(JPQLQuery<FavoriteEntity> query) {
        return query.select(favoriteEntity, openRecipeEntity, favoriteEntity.recipe.count())
                .leftJoin(openRecipeEntity).on(favoriteEntity.recipe.id.eq(openRecipeEntity.id))
                .from(favoriteEntity);
    }

    private JPQLQuery<Tuple> selectFavoriteRecipe(JPQLQuery<FavoriteEntity> query) {
        return query.select(favoriteEntity, openRecipeEntity)
                .leftJoin(openRecipeEntity).on(favoriteEntity.recipe.id.eq(openRecipeEntity.id))
                .from(favoriteEntity);
    }

    private JPQLQuery<Long> selectFavoriteRecipeId(JPQLQuery<FavoriteEntity> query) {
        return query.select(openRecipeEntity.id)
                .leftJoin(openRecipeEntity).on(favoriteEntity.recipe.id.eq(openRecipeEntity.id))
                .from(favoriteEntity);
    }

    private JPQLQuery<Tuple> orderFavoriteRecipe(JPQLQuery<Tuple> selectQuery) {
        int rankTotal = 8;
        return selectQuery.groupBy(favoriteEntity.recipe.rcpSeq).orderBy(favoriteEntity.recipe.id.count().desc()).limit(rankTotal);
    }

    private JPQLQuery<Tuple> whereFavoriteRecipe(JPQLQuery<Tuple> selectQuery, String email) {
        return selectQuery.where(favoriteEntity.userEmail.eq(email)).groupBy(favoriteEntity.recipe.rcpSeq);
    }

    private JPQLQuery<Long> whereFavoriteRecipeId(JPQLQuery<Long> selectQuery, String email) {
        return selectQuery.where(favoriteEntity.userEmail.eq(email)).groupBy(favoriteEntity.recipe.rcpSeq);
    }
}
