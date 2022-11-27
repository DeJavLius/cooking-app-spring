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
        int oneLine = 8;

        JPQLQuery<Tuple> difficultSearch = from(favoriteRecipeEntity).select(favoriteRecipeEntity.recipeSeq, favoriteRecipeEntity.recipeSeq.count())
                .from(favoriteRecipeEntity).groupBy(favoriteRecipeEntity.recipeSeq).orderBy(favoriteRecipeEntity.recipeSeq.count().desc()).limit(oneLine);
        return difficultSearch.fetch();
    }
}
