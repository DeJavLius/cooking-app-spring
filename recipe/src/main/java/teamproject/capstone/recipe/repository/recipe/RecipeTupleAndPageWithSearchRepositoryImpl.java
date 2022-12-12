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
        NumberPath<Long> aliasRecipe = Expressions.numberPath(Long.class, "id");
        JPAQuery<Tuple> openAPIDataHandle = withSelectInit(aliasRecipe);
        return pagingWithSortHandler(openAPIDataHandle, aliasRecipe, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchOrPageHandling(Search searchKeywords, Pageable pageable) {
        JPAQuery<OpenRecipeEntity> openAPIDataHandle = jpaQueryStart();
        openAPIDataHandle.where(searchOrQueryBuilder(searchKeywords));
        return pagingHandler(openAPIDataHandle, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> openAPISearchAndPageHandling(Search searchKeywords, Pageable pageable) {
        NumberPath<Long> aliasRecipe = Expressions.numberPath(Long.class, "id");
        JPAQuery<Tuple> openAPIDataHandle = withSelectInit(aliasRecipe);
        openAPIDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingWithSortHandler(openAPIDataHandle, aliasRecipe, pageable);
    }

    @Override
    public Page<OpenRecipeEntity> recipeSearchAndPageHandling(Search searchKeywords, Pageable pageable) {
        NumberPath<Long> aliasRecipe = Expressions.numberPath(Long.class, "id");
        JPAQuery<Tuple> recipeDataHandle = withSelectInit(aliasRecipe);
        recipeDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingWithSortHandler(recipeDataHandle, aliasRecipe, pageable);
    }

    @Override
    public Page<Object[]> recipeSearchAndPageSeparateHandling(Search searchKeywords, Pageable pageable) {
        NumberPath<Long> aliasRecipe = Expressions.numberPath(Long.class, "id");
        JPAQuery<Tuple> tupleJPAQuery = separateSelectInit(aliasRecipe);
        tupleJPAQuery.where(searchAndQueryBuilder(searchKeywords));
        return tuplePagingWithSortHandler(tupleJPAQuery, aliasRecipe, pageable);
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

    private JPAQuery<Tuple> separateSelectInit(NumberPath<Long> aliasRecipe) {
        return jpaQuerySeparateStart(aliasRecipe).leftJoin(favoriteEntity).on(favoriteEntity.recipe.id.eq(openRecipeEntity.id)).groupBy(openRecipeEntity.id);
    }

    private JPAQuery<Tuple> withSelectInit(NumberPath<Long> aliasRecipe) {
        return jpaQueryWithCountStart(aliasRecipe).leftJoin(favoriteEntity).on(favoriteEntity.recipe.id.eq(openRecipeEntity.id)).groupBy(openRecipeEntity.id);
    }

    private JPAQuery<OpenRecipeEntity> jpaQueryStart() {
        return jpaQueryOpenInit().from(openRecipeEntity).select(openRecipeEntity);
    }

    private JPAQuery<Tuple> jpaQueryWithCountStart(NumberPath<Long> aliasRecipe) {
        return jpaQueryOpenInit().from(openRecipeEntity).select(openRecipeEntity, favoriteEntity.recipe.id.count().as(aliasRecipe));
    }

    private JPAQuery<Tuple> jpaQuerySeparateStart(NumberPath<Long> aliasRecipe) {
        return jpaQueryOpenInit().from(openRecipeEntity).select(openRecipeEntity.id, openRecipeEntity.attFileNoMain, openRecipeEntity.rcpPat2, openRecipeEntity.rcpNm, favoriteEntity.recipe.id.count().as(aliasRecipe));
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

    private Page<OpenRecipeEntity> pagingWithSortHandler(JPAQuery<Tuple> query, NumberPath<Long> aliasRecipe, Pageable pageable) {
        totalCountSetting(query.fetch().size());
        pageSortSetting(query, aliasRecipe, pageable.getSort());

        List<Tuple> tupleResult = sqlTuplePageSetting(query, pageable);
        List<OpenRecipeEntity> result = tupleResult.stream().map(tuple -> (OpenRecipeEntity) tuple.toArray()[0]).collect(Collectors.toList());
        long count = TotalValue.getTotalCount();
        return new PageImpl<>(result, pageable, count);
    }

    private Page<Object[]> tuplePagingWithSortHandler(JPAQuery<Tuple> query, NumberPath<Long> aliasRecipe, Pageable pageable) {
        totalCountSetting(query.fetch().size());
        pageSortSetting(query, aliasRecipe, pageable.getSort());

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

    private void pageSortSetting(JPQLQuery<Tuple> query, NumberPath<Long> aliasRecipe, Sort pageSort) {
        pageSort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(OpenRecipeEntity.class, "openRecipeEntity");
            if (prop.equals("favorite")) {
                query.orderBy(aliasRecipe.desc());
            } else {
                query.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
            }
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
