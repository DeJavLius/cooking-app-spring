package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.entity.recipe.QFavoriteEntity;

import java.util.List;

@Repository
public class FavoriteRankRepositoryImpl extends QuerydslRepositorySupport implements FavoriteRankRepository {
    private final QFavoriteEntity favoriteEntity = QFavoriteEntity.favoriteEntity;

    public FavoriteRankRepositoryImpl() {
        super(FavoriteEntity.class);
    }

    @Override
    public List<Tuple> findWithRankFavoriteRecipe() {
        JPQLQuery<Tuple> selectFavoriteRecipe = selectFavoriteRecipe(from(favoriteEntity));
        return orderFavoriteRecipe(selectFavoriteRecipe).fetch();
    }

    @Override
    public List<Long> findAllFavoriteRecipe(String email) {
        JPQLQuery<Long> allFavoriteList = selectRecipeSeqOnly();
        return allFavoriteList.fetch();
    }

    public JPQLQuery<Tuple> selectFavoriteRecipe(JPQLQuery<FavoriteEntity> query) {
        return query.select(favoriteEntity.recipe.rcpSeq, favoriteEntity.recipe.rcpSeq.count())
                .from(favoriteEntity);
    }

    public JPQLQuery<Long> selectRecipeSeqOnly() {
        return from(favoriteEntity).select(favoriteEntity.recipe.rcpSeq);
    }

    public JPQLQuery<Tuple> orderFavoriteRecipe(JPQLQuery<Tuple> selectQuery) {
        int oneLine = 8;

        return selectQuery.groupBy(favoriteEntity.recipe.rcpSeq).orderBy(favoriteEntity.recipe.rcpSeq.count().desc()).limit(oneLine);
    }
}
