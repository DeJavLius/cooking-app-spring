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
import teamproject.capstone.recipe.utils.page.TotalValue;

import java.util.List;

@Slf4j
@Repository
public class RecipeTupleAndPageWithSearchRepositoryImpl extends QuerydslRepositorySupport implements OpenRecipePageWithSearchRepository, RecipeTupleRepository {
    private final QOpenRecipeEntity openRecipeEntity = QOpenRecipeEntity.openRecipeEntity;

    public RecipeTupleAndPageWithSearchRepositoryImpl() {
        super(OpenRecipeEntity.class);
    }

    @Override
    public Page<OpenRecipeEntity> openAPIPageHandling(Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchOrPageHandling(Search searchKeywords, Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        openAPIDataHandle.where(searchOrQueryBuilder(searchKeywords));
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchAndPageHandling(Search searchKeywords, Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        openAPIDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> recipeSearchAndPageHandling(Search searchKeywords, Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> recipeDataHandle = jpqlQueryInit();
        recipeDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingWithSortHandler(recipeDataHandle, pageable);
    }

    @Override
    public List<String> recipeWayExtract() {
        JPQLQuery<String> recipeWayList = jpqlQuerySelectWayInit();
        recipeWayList.groupBy(openRecipeEntity.rcpWay2);
        return recipeWayList.fetch();
    }

    @Override
    public List<String> recipePartExtract() {
        JPQLQuery<String> recipeWayList = jpqlQuerySelectPartInit();
        recipeWayList.groupBy(openRecipeEntity.rcpPat2);
        return recipeWayList.fetch();
    }

    private JPQLQuery<OpenRecipeEntity>  jpqlQueryInit() {
        JPQLQuery<OpenRecipeEntity> jpqlQuery = from(openRecipeEntity);
        return jpqlQuery.select(openRecipeEntity);
    }

    private JPQLQuery<String>  jpqlQuerySelectWayInit() {
        return from(openRecipeEntity).select(openRecipeEntity.rcpWay2);
    }

    private JPQLQuery<String>  jpqlQuerySelectPartInit() {
        return from(openRecipeEntity).select(openRecipeEntity.rcpPat2);
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

    private BooleanBuilder searchOrQueryBuilder(Search keywords) {
        BooleanBuilder queryResult = defaultBooleanBuilder();
        queryResult.and(conditionOrBuilders(queryResult, keywords));
        return queryResult;
    }

    private BooleanBuilder searchAndQueryBuilder(Search keywords) {
        BooleanBuilder queryResult = defaultBooleanBuilder();
        queryResult.and(conditionAndBuilders(queryResult, keywords));
        return queryResult;
    }

    private BooleanBuilder defaultBooleanBuilder() {
        BooleanExpression booleanExpression = openRecipeEntity.id.gt(0L);
        return new BooleanBuilder().and(booleanExpression);
    }

    private BooleanBuilder conditionAndBuilders(BooleanBuilder condition, Search keywords) {
        if (!keywords.getName().isEmpty()) {
            condition.and(openRecipeEntity.rcpNm.contains(keywords.getName()));
        }
        if (!keywords.getDetail().isEmpty()) {
            condition.and(openRecipeEntity.rcpPartsDtls.contains(keywords.getDetail()));
        }
        if (!keywords.getPart().isEmpty()) {
            condition.and(openRecipeEntity.rcpPat2.contains(keywords.getPart()));
        }
        if (keywords.getSeq() > 0L) {
            condition.and(openRecipeEntity.rcpSeq.eq(keywords.getSeq()));
        }
        if (!keywords.getWay().isEmpty()) {
            condition.and(openRecipeEntity.rcpWay2.contains(keywords.getWay()));
        }

        return condition;
    }

    private BooleanBuilder conditionOrBuilders(BooleanBuilder condition, Search keywords) {
        if (!keywords.getName().isEmpty()) {
            condition.or(openRecipeEntity.rcpNm.contains(keywords.getName()));
        }
        if (!keywords.getDetail().isEmpty()) {
            condition.or(openRecipeEntity.rcpPartsDtls.contains(keywords.getDetail()));
        }
        if (!keywords.getPart().isEmpty()) {
            condition.or(openRecipeEntity.rcpPat2.contains(keywords.getPart()));
        }
        if (keywords.getSeq() > 0L) {
            condition.or(openRecipeEntity.rcpSeq.eq(keywords.getSeq()));
        }
        if (!keywords.getWay().isEmpty()) {
            condition.or(openRecipeEntity.rcpWay2.contains(keywords.getWay()));
        }

        return condition;
    }
}
