package teamproject.capstone.recipe.repository.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    Optional<List<FavoriteEntity>> findByUserEmail(String email);
    Optional<List<FavoriteEntity>> findByRecipeSeq(long recipeSeq);
    Optional<FavoriteEntity> findByRecipeSeqAndUserEmail(long recipeSeq, String userEmail);
    void deleteByUserEmail(String email);
    void deleteByUserEmailAndRecipeSeq(String email, Long seq);
}
