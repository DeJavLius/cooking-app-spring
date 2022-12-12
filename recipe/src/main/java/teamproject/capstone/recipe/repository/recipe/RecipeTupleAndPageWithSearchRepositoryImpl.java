package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.entity.recipe.QFavoriteEntity;
import teamproject.capstone.recipe.entity.recipe.QOpenRecipeEntity;
import teamproject.capstone.recipe.utils.page.*;
import teamproject.capstone.recipe.utils.queryDSL.MySqlJpaTemplates;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class RecipeTupleAndPageWithSearchRepositoryImpl extends QuerydslRepositorySupport implements OpenRecipePageWithSearchRepository, RecipeTupleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QOpenRecipeEntity openRecipeEntity = QOpenRecipeEntity.openRecipeEntity;
    private final QFavoriteEntity favoriteEntity = QFavoriteEntity.favoriteEntity;

    public RecipeTupleAndPageWithSearchRepositoryImpl() {
        super(OpenRecipeEntity.class);
    }

    @Override
    public Page<OpenRecipeEntity> openAPIPageHandling(Pageable pageable) {
        JPAQuery<OpenRecipeEntity> openAPIDataHandle = jpaQueryStart();
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchOrPageHandling(Search searchKeywords, Pageable pageable) {
        JPAQuery<OpenRecipeEntity> openAPIDataHandle = jpaQueryStart();
        openAPIDataHandle.where(searchOrQueryBuilder(searchKeywords));
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchAndPageHandling(Search searchKeywords, Pageable pageable) {
        JPAQuery<OpenRecipeEntity> openAPIDataHandle = jpaQueryStart();
        openAPIDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> recipeSearchAndPageHandling(Search searchKeywords, Pageable pageable) {
        JPAQuery<OpenRecipeEntity> recipeDataHandle = jpaQueryStart();
        recipeDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingWithSortHandler(recipeDataHandle, pageable);
    }

    @Override
    public Page<Object[]> recipeSearchAndPageSeparateHandling(Search searchKeywords, Pageable pageable) {
        JPAQuery<Tuple> tupleJPAQuery = separateSelectInit();
        tupleJPAQuery.where(searchAndQueryBuilder(searchKeywords));
        return tuplePagingWithSortHandler(tupleJPAQuery, pageable);
    }

    @Override
    public List<String> recipeWayExtract() {
        JPAQuery<String> recipeWayList = jpaQuerySelectWayInit();
        recipeWayList.groupBy(openRecipeEntity.rcpWay2);
        return recipeWayList.fetch();
    }

    @Override
    public List<String> recipePartExtract() {
        JPAQuery<String> recipeWayList = jpaQuerySelectPartInit();
        recipeWayList.groupBy(openRecipeEntity.rcpPat2);
        return recipeWayList.fetch();
    }

    @Override
    public List<Object[]> sameRecommendRecipe(Search search) {
        JPAQuery<Tuple> recipeRecommend = jpaQuerySelectRecommendRandInit();
        recipeRecommend.where(searchAndQueryBuilder(search));
        recipeRecommend.orderBy(NumberExpression.random().asc()).limit(4);
        List<Tuple> fetch = recipeRecommend.fetch();
        return fetch.stream().map(Tuple::toArray).collect(Collectors.toList());
    }

    private JPAQuery<Tuple> separateSelectInit() {
        return jpaQuerySeparateStart().leftJoin(favoriteEntity).on(favoriteEntity.recipe.id.eq(openRecipeEntity.id)).groupBy(openRecipeEntity.id);
    }

    private JPAQuery<OpenRecipeEntity> jpaQueryStart() {
        return jpaQueryOpenInit().from(openRecipeEntity).select(openRecipeEntity);
    }

    private JPAQuery<Tuple> jpaQuerySeparateStart() {
        return jpaQueryOpenInit().from(openRecipeEntity).select(openRecipeEntity.id, openRecipeEntity.attFileNoMain, openRecipeEntity.rcpPat2, openRecipeEntity.rcpNm, favoriteEntity.recipe.id.count());
    }

    private JPAQuery<String> jpaQuerySelectWayInit() {
        return jpaQueryOpenInit().from(openRecipeEntity).select(openRecipeEntity.rcpWay2);
    }

    private JPAQuery<String> jpaQuerySelectPartInit() {
        return jpaQueryOpenInit().from(openRecipeEntity).select(openRecipeEntity.rcpPat2);
    }

    private JPAQuery<Tuple> jpaQuerySelectRecommendRandInit() {
        return jpaQueryMySqlTemplateInit().from(openRecipeEntity).select(openRecipeEntity.id, openRecipeEntity.attFileNoMain, openRecipeEntity.rcpNm);
    }

    private JPAQuery<OpenRecipeEntity> jpaQueryOpenInit() {
        return new JPAQuery<>(entityManager);
    }

    private JPAQuery<OpenRecipeEntity> jpaQueryMySqlTemplateInit() {
        return new JPAQuery<>(entityManager, MySqlJpaTemplates.DEFAULT);
    }

    private Page<OpenRecipeEntity> pagingHandler(JPAQuery<OpenRecipeEntity> query, Pageable pageable) {
        totalCountSetting(query.fetch().size());

        List<OpenRecipeEntity> result = sqlPageSetting(query, pageable);
        long count = TotalValue.getTotalCount();
        return new PageImpl<>(result, pageable, count);
    }

    private Page<OpenRecipeEntity> pagingWithSortHandler(JPAQuery<OpenRecipeEntity> query, Pageable pageable) {
        totalCountSetting(query.fetch().size());
        pageSortSetting(query, pageable.getSort());

        List<OpenRecipeEntity> result = sqlPageSetting(query, pageable);
        long count = TotalValue.getTotalCount();
        return new PageImpl<>(result, pageable, count);
    }

    private Page<Object[]> tuplePagingWithSortHandler(JPAQuery<Tuple> query, Pageable pageable) {
        totalCountSetting(query.fetch().size());
        tuplePageSortSetting(query, pageable.getSort());

        List<Tuple> tuples = sqlTuplePageSetting(query, pageable);
        List<Object[]> result = tuples.stream().map(Tuple::toArray).collect(Collectors.toList());
        long count = TotalValue.getTotalCount();
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

    private void tuplePageSortSetting(JPAQuery<Tuple> query, Sort pageSort) {
        pageSort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC: Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(OpenRecipeEntity.class, "openRecipeEntity");
            query.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
    }

    private List<OpenRecipeEntity> sqlPageSetting(JPAQuery<OpenRecipeEntity> openAPIDataHandle, Pageable pageable) {
        log.info("page offset value : {} / page size value : {}", pageable.getOffset(), pageable.getPageSize());
        openAPIDataHandle.offset(pageable.getOffset()).limit(pageable.getPageSize());
        return openAPIDataHandle.fetch();
    }

    private List<Tuple> sqlTuplePageSetting(JPAQuery<Tuple> openAPIDataHandle, Pageable pageable) {
        log.info("page offset value : {} / page size value : {}", pageable.getOffset(), pageable.getPageSize());
        openAPIDataHandle.offset(pageable.getOffset()).limit(pageable.getPageSize());
        return openAPIDataHandle.fetch();
    }

    private BooleanBuilder searchOrQueryBuilder(Search keywords) {
        BooleanBuilder queryResult = defaultBooleanBuilder();
        queryResult.and(detailQuery(keywords.getDetail())
                .or(nameQuery(keywords.getName()))
                .or(partQuery(keywords.getPart()))
                .or(wayQuery(keywords.getWay()))
                .or(seqQuery(keywords.getSeq()))
        );
        return queryResult;
    }

    private BooleanBuilder searchAndQueryBuilder(Search keywords) {
        BooleanBuilder queryResult = defaultBooleanBuilder();
        queryResult.and(detailQuery(keywords.getDetail())
                .and(nameQuery(keywords.getName()))
                .and(partQuery(keywords.getPart()))
                .and(wayQuery(keywords.getWay()))
        );

        if (keywords.getSeq() > 0L) {
            queryResult.and(seqQuery(keywords.getSeq()));
        }
        return queryResult;
    }

    private BooleanBuilder defaultBooleanBuilder() {
        BooleanExpression booleanExpression = openRecipeEntity.id.gt(0L);
        return new BooleanBuilder().and(booleanExpression);
    }

    private BooleanExpression nameQuery(String name) {
        return openRecipeEntity.rcpNm.contains(name);
    }

    private BooleanExpression detailQuery(String detail) {
        return openRecipeEntity.rcpPartsDtls.contains(detail);
    }

    private BooleanExpression partQuery(String part) {
        return openRecipeEntity.rcpPat2.contains(part);
    }

    private BooleanExpression wayQuery(String way) {
        return openRecipeEntity.rcpWay2.contains(way);
    }

    private BooleanExpression seqQuery(Long seq) {
        return openRecipeEntity.rcpSeq.eq(seq);
    }
}
