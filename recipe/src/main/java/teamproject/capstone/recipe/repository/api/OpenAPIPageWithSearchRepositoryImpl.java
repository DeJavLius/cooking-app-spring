package teamproject.capstone.recipe.repository.api;

import com.querydsl.core.BooleanBuilder;
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
        totalCountSetting((int) openAPIDataHandle.fetchCount());

        List<OpenRecipeEntity> result = sqlPageSetting(openAPIDataHandle, pageable);
        long count = openAPIDataHandle.fetchCount();
        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchPageHandling(List<APISearch> searchKeywords, Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();
        totalCountSetting((int) openAPIDataHandle.fetchCount());

        List<OpenRecipeEntity> result = sqlPageSetting(openAPIDataHandle, pageable);
        long count = openAPIDataHandle.fetchCount();
        return new PageImpl<>(result, pageable, count);
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

    private List<OpenRecipeEntity> sqlPageSetting(JPQLQuery<OpenRecipeEntity> openAPIDataHandle, Pageable pageable) {
        log.info("page offset value : {} / page size value : {}", pageable.getOffset(), pageable.getPageSize());
        openAPIDataHandle.offset(pageable.getOffset()).limit(pageable.getPageSize());
        return openAPIDataHandle.fetch();
    }

    private BooleanBuilder searchQueryBuilder(List<APISearch> keywords) {
        BooleanBuilder booleanBuilder = defaultBooleanBuilder();
    }

    private BooleanBuilder defaultBooleanBuilder() {
        BooleanExpression booleanExpression = openRecipeEntity.id.gt(0L);
        return new BooleanBuilder().and(booleanExpression);
    }

    private BooleanBuilder conditionBuilders(BooleanBuilder condition, List<APISearch> keywords) {
        for (APISearch search : keywords) {
            condition = conditionBuilder(condition, search);
        }
    }

    private BooleanBuilder conditionBuilder(BooleanBuilder condition, APISearch search) {
        return condition.or();
    }
}
