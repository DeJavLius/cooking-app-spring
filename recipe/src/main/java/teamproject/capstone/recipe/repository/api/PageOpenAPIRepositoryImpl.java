package teamproject.capstone.recipe.repository.api;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.api.OpenAPIEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PageOpenAPIRepositoryImpl extends QuerydslRepositorySupport implements PageOpenAPIRepository {
    @PersistenceContext
    private EntityManager em;

    private QOpenAPIEntity openAPIEntity = QOpenAPIEntity.openAPIEntity;

    public PageOpenAPIRepositoryImpl() {
        super(OpenAPIEntity.class);
    }

    @Override
    public Page<Object[]> openAPIPageHandling(Pageable pageable) {
        JPQLQuery<Tuple> openAPIHandle = jpqlQueryInit();

        openAPIHandle.offset(pageable.getOffset());
        openAPIHandle.limit(pageable.getPageSize());

        List<Tuple> result = openAPIHandle.fetch();

        long count = openAPIHandle.fetchCount();
        return new PageImpl<>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);
    }

    public JPQLQuery<Tuple> jpqlQueryInit() {
        JPQLQuery<OpenAPIEntity> jpqlQuery = from(openAPIEntity);
        return jpqlQuery.select(openAPIEntity);
    }
}
