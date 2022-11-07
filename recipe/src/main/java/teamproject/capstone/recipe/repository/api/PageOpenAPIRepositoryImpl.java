package teamproject.capstone.recipe.repository.api;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.entity.api.QOpenRecipeEntity;

import java.util.List;

@Slf4j
@Repository
public class PageOpenAPIRepositoryImpl extends QuerydslRepositorySupport implements PageOpenAPIRepository {

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
        log.info("page offset value : {} / page size value : {}", pageable.getOffset(), pageable.getPageSize());
        openAPIDataHandle.offset(pageable.getOffset()).limit(pageable.getPageSize());
        return openAPIDataHandle.fetch();
    }
}
