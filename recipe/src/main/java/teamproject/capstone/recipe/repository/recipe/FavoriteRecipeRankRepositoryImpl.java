package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.recipe.FavoriteRecipeEntity;
import teamproject.capstone.recipe.entity.recipe.QFavoriteRecipeEntity;

import java.util.List;

@Repository
public class FavoriteRecipeRankRepositoryImpl extends QuerydslRepositorySupport implements FavoriteRecipeRankRepository {
    private final QFavoriteRecipeEntity favoriteRecipeEntity = QFavoriteRecipeEntity.favoriteRecipeEntity;

    public FavoriteRecipeRankRepositoryImpl() {
        super(FavoriteRecipeEntity.class);
    }

    @Override
    public List<Tuple> findWithRankFavoriteRecipe() {
        JPQLQuery<Tuple> selectFavoriteRecipe = selectFavoriteRecipe(from(favoriteRecipeEntity));
        return orderFavoriteRecipe(selectFavoriteRecipe).fetch();
    }

    public JPQLQuery<Tuple> selectFavoriteRecipe(JPQLQuery<FavoriteRecipeEntity> query) {
        return query.select(favoriteRecipeEntity.recipeSeq, favoriteRecipeEntity.recipeSeq.count())
                .from(favoriteRecipeEntity);
    }

    public JPQLQuery<Tuple> orderFavoriteRecipe(JPQLQuery<Tuple> selectQuery) {
        int oneLine = 8;

        return selectQuery.groupBy(favoriteRecipeEntity.recipeSeq).orderBy(favoriteRecipeEntity.recipeSeq.count().desc()).limit(oneLine);
    }
}
