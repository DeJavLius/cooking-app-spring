package teamproject.capstone.recipe.repository.api;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.entity.api.QOpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.APISearch;
import teamproject.capstone.recipe.utils.values.SearchType;
import teamproject.capstone.recipe.utils.values.TotalValue;

import java.util.List;

@Slf4j
@Repository
public class OpenAPIPageWithSearchRepositoryImpl extends QuerydslRepositorySupport implements OpenAPIPageRepository, OpenAPISearchRepository {
    private final QOpenRecipeEntity openRecipeEntity = QOpenRecipeEntity.openRecipeEntity;

    public OpenAPIPageWithSearchRepositoryImpl() {
        super(OpenRecipeEntity.class);
    }

    @Override
    public Page<OpenRecipeEntity> openAPIPageHandling(Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchOrPageHandling(List<APISearch> searchKeywords, Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        openAPIDataHandle.where(searchOrQueryBuilder(searchKeywords));
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchAndPageHandling(List<APISearch> searchKeywords, Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        openAPIDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingHandler(openAPIDataHandle, pageable);
    }

    private JPQLQuery<OpenRecipeEntity> jpqlQueryInit() {
        JPQLQuery<OpenRecipeEntity> jpqlQuery = from(openRecipeEntity);
        return jpqlQuery.select(openRecipeEntity);
    }

    private void totalCountSetting(int count) {
        if (TotalValue.getTotalCount() != count) {
            TotalValue.setTotalCount(count);
        }
    }

    private Page<OpenRecipeEntity> pagingHandler(JPQLQuery<OpenRecipeEntity> query, Pageable pageable) {
        totalCountSetting((int) query.fetchCount());
        List<OpenRecipeEntity> result = sqlPageSetting(query, pageable);
        long count = query.fetchCount();
        return new PageImpl<>(result, pageable, count);
    }

    private List<OpenRecipeEntity> sqlPageSetting(JPQLQuery<OpenRecipeEntity> openAPIDataHandle, Pageable pageable) {
        log.info("page offset value : {} / page size value : {}", pageable.getOffset(), pageable.getPageSize());
        openAPIDataHandle.offset(pageable.getOffset()).limit(pageable.getPageSize());
        return openAPIDataHandle.fetch();
    }

    private BooleanBuilder searchOrQueryBuilder(List<APISearch> keywords) {
        BooleanBuilder queryResult = defaultBooleanBuilder();
        queryResult.and(conditionOrBuilders(queryResult, keywords));
        return queryResult;
    }

    private BooleanBuilder searchAndQueryBuilder(List<APISearch> keywords) {
        BooleanBuilder queryResult = defaultBooleanBuilder();
        queryResult.and(conditionAndBuilders(queryResult, keywords));
        return queryResult;
    }

    private BooleanBuilder defaultBooleanBuilder() {
        BooleanExpression booleanExpression = openRecipeEntity.id.gt(0L);
        return new BooleanBuilder().and(booleanExpression);
    }

    private BooleanBuilder conditionOrBuilders(BooleanBuilder condition, List<APISearch> keywords) {
        for (APISearch search : keywords) {
            condition.or(conditionBuilder(search));
        }

        return condition;
    }

    private BooleanBuilder conditionAndBuilders(BooleanBuilder condition, List<APISearch> keywords) {
        for (APISearch search : keywords) {
            condition.and(conditionBuilder(search));
        }

        return condition;
    }

    private BooleanExpression conditionBuilder(APISearch search) {
        return typeContains(search);
    }

    private BooleanExpression typeContains(APISearch search) {
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
