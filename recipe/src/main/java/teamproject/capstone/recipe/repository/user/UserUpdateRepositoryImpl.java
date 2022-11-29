package teamproject.capstone.recipe.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.entity.user.QUserEntity;
import teamproject.capstone.recipe.entity.user.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserUpdateRepositoryImpl extends QuerydslRepositorySupport implements UserUpdateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final QUserEntity userEntity = QUserEntity.userEntity;

    public UserUpdateRepositoryImpl() {
        super(OpenRecipeEntity.class);
    }

    @Transactional
    @Override
    public UserEntity updateBeAppUser(UserEntity updateEntity) {
        JPAQueryFactory jpaQueryFactory = jpaQueryFactoryGenerator();
        jpaQueryFactory.update(userEntity)
                .set(userEntity.uid, updateEntity.getUid())
                .where(userEntity.id.eq(updateEntity.getId()))
                .execute();

        return updateEntity;
    }

    private JPAQueryFactory jpaQueryFactoryGenerator() {
        return new JPAQueryFactory(entityManager);
    }
}
