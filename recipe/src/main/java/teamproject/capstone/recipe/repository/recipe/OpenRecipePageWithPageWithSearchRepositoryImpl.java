package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.entity.recipe.QOpenRecipeEntity;
import teamproject.capstone.recipe.utils.page.Search;
import teamproject.capstone.recipe.utils.values.*;

import java.util.List;

@Slf4j
@Repository
public class OpenRecipePageWithPageWithSearchRepositoryImpl extends QuerydslRepositorySupport implements OpenRecipePageWithSearchRepository {
    private final QOpenRecipeEntity openRecipeEntity = QOpenRecipeEntity.openRecipeEntity;

    public OpenRecipePageWithPageWithSearchRepositoryImpl() {
        super(OpenRecipeEntity.class);
    }

    @Override
    public Page<OpenRecipeEntity> openAPIPageHandling(Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchOrPageHandling(List<Search> searchKeywords, Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        openAPIDataHandle.where(searchOrQueryBuilder(searchKeywords));
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchAndPageHandling(List<Search> searchKeywords, Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        openAPIDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> recipeSearchAndPageHandling(List<Search> searchKeywords, Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> recipeDataHandle = jpqlQueryInit();
        recipeDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingWithSortHandler(recipeDataHandle, pageable);
    }

    private JPQLQuery<OpenRecipeEntity>  jpqlQueryInit() {
        JPQLQuery<OpenRecipeEntity> jpqlQuery = from(openRecipeEntity);
        return jpqlQuery.select(openRecipeEntity);
    }

    private Page<OpenRecipeEntity> pagingHandler(JPQLQuery<OpenRecipeEntity> query, Pageable pageable) {
        totalCountSetting((int) query.fetchCount());
        List<OpenRecipeEntity> result = sqlPageSetting(query, pageable);
        long count = query.fetchCount();
        return new PageImpl<>(result, pageable, count);
    }

    private Page<OpenRecipeEntity> pagingWithSortHandler(JPQLQuery<OpenRecipeEntity> query, Pageable pageable) {
        totalCountSetting((int) query.fetchCount());
        pageSortSetting(query, pageable.getSort());

        List<OpenRecipeEntity> result = sqlPageSetting(query, pageable);
        long count = query.fetchCount();
        return new PageImpl<>(result, pageable, count);
    }

    private void totalCountSetting(int count) {
        if (TotalValue.getTotalCount() != count) {
            TotalValue.setTotalCount(count);
        }
    }

    private void pageSortSetting(JPQLQuery<OpenRecipeEntity> query, Sort pageSort) {
        pageSort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC: Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(OpenRecipeEntity.class, "openRecipeEntity");
            query.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
    }

    private List<OpenRecipeEntity> sqlPageSetting(JPQLQuery<OpenRecipeEntity> openAPIDataHandle, Pageable pageable) {
        log.info("page offset value : {} / page size value : {}", pageable.getOffset(), pageable.getPageSize());
        openAPIDataHandle.offset(pageable.getOffset()).limit(pageable.getPageSize());
        return openAPIDataHandle.fetch();
    }

    private BooleanBuilder searchOrQueryBuilder(List<Search> keywords) {
        BooleanBuilder queryResult = defaultBooleanBuilder();
        queryResult.and(conditionOrBuilders(queryResult, keywords));
        return queryResult;
    }

    private BooleanBuilder searchAndQueryBuilder(List<Search> keywords) {
        BooleanBuilder queryResult = defaultBooleanBuilder();
        queryResult.and(conditionAndBuilders(queryResult, keywords));
        return queryResult;
    }

    private BooleanBuilder defaultBooleanBuilder() {
        BooleanExpression booleanExpression = openRecipeEntity.id.gt(0L);
        return new BooleanBuilder().and(booleanExpression);
    }

    private BooleanBuilder conditionOrBuilders(BooleanBuilder condition, List<Search> keywords) {
        for (Search search : keywords) {
            condition.or(conditionBuilder(search));
        }

        return condition;
    }

    private BooleanBuilder conditionAndBuilders(BooleanBuilder condition, List<Search> keywords) {
        for (Search search : keywords) {
            condition.and(conditionBuilder(search));
        }

        return condition;
    }

    private BooleanExpression conditionBuilder(Search search) {
        try {
            return typeContains(search);
        } catch (Exception e) {
            log.error("wrong search value request... : log detail", e);
            return openRecipeEntity.id.in(0L);
        }
    }

    private BooleanExpression typeContains(Search search) {
        log.info("value test of search keyword : {}, type : {}", search.getKeyword(), search.getType());
        if (search.getType().equals(SearchType.RECIPE_NAME.getValue()) & !search.getKeyword().isEmpty()) {
            return openRecipeEntity.rcpNm.contains(search.getKeyword());
        }
        if (search.getType().equals(SearchType.RECIPE_DETAILS.getValue()) & !search.getKeyword().isEmpty()) {
            return openRecipeEntity.rcpPartsDtls.contains(search.getKeyword());
        }
        if (search.getType().equals(SearchType.RECIPE_PARTS.getValue()) & !search.getKeyword().isEmpty()) {
            return openRecipeEntity.rcpPat2.contains(search.getKeyword());
        }
        if (search.getType().equals(SearchType.RECIPE_SEQUENCE.getValue()) & !search.getKeyword().equals("0")) {
            return openRecipeEntity.rcpSeq.eq(Long.parseLong(search.getKeyword()));
        }
        if (search.getType().equals(SearchType.RECIPE_WAY.getValue()) & !search.getKeyword().isEmpty()) {
            return openRecipeEntity.rcpWay2.eq(search.getKeyword());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
