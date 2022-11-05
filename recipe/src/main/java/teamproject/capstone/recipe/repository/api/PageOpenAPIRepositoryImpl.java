package teamproject.capstone.recipe.repository.api;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.entity.api.QOpenRecipeEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PageOpenAPIRepositoryImpl extends QuerydslRepositorySupport implements PageOpenAPIRepository {
    @PersistenceContext
    private EntityManager em;

    private final QOpenRecipeEntity openRecipeEntity = QOpenRecipeEntity.openRecipeEntity;

    public PageOpenAPIRepositoryImpl() {
        super(OpenRecipeEntity.class);
    }

    @Override
    public Page<OpenRecipeEntity> openAPIPageHandling(Pageable pageable) {
        JPQLQuery<OpenRecipeEntity> openAPIDataHandle = jpqlQueryInit();

        List<OpenRecipeEntity> result = sqlPageSetting(openAPIDataHandle, pageable);
        long count = openAPIDataHandle.fetchCount();
        return new PageImpl<>(result, pageable, count);
    }

    private JPQLQuery<OpenRecipeEntity> jpqlQueryInit() {
        JPQLQuery<OpenRecipeEntity> jpqlQuery = from(openRecipeEntity);
        return jpqlQuery.select(openRecipeEntity);
    }

    private List<OpenRecipeEntity> sqlPageSetting(JPQLQuery<OpenRecipeEntity> openAPIDataHandle, Pageable pageable) {
        openAPIDataHandle.offset(pageable.getOffset()).limit(pageable.getPageSize());
        return openAPIDataHandle.fetch();
    }
}
