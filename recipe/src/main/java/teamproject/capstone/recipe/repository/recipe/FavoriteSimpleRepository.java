package teamproject.capstone.recipe.repository.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;

@Repository
public interface FavoriteSimpleRepository extends JpaRepository<FavoriteEntity, Long> {
    void deleteByUserEmail(String email);
}
