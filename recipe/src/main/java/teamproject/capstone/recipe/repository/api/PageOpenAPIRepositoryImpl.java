package teamproject.capstone.recipe.repository.api;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PageOpenAPIRepositoryImpl extends QuerydslRepositorySupport implements PageOpenAPIRepository {
    @PersistenceContext
    private EntityManager em;

    private QOpenRecipeEntity openRecipeEntity = QOpenRecipeEntity.openRecipeEntity;

    public PageOpenAPIRepositoryImpl() {
        super(OpenRecipeEntity.class);
    }

    @Override
    public Page<Object[]> openAPIPageHandling(Pageable pageable) {
        JPQLQuery<Tuple> openAPIDataHandle = jpqlQueryInit();

        openAPIDataHandle.offset(pageable.getOffset());
        openAPIDataHandle.limit(pageable.getPageSize());

        List<Tuple> result = openAPIDataHandle.fetch();

        long count = openAPIDataHandle.fetchCount();
        return new PageImpl<>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
    }

    public JPQLQuery<Tuple> jpqlQueryInit() {
//        JPQLQuery<OpenRecipeEntity> jpqlQuery = from(openRecipeEntity);
//        return jpqlQuery.select(openRecipeEntity);
        return null;
    }
}
