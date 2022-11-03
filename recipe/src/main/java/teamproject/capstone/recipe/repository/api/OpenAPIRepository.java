package teamproject.capstone.recipe.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.capstone.recipe.entity.api.OpenAPIEntity;

@Repository
public interface OpenAPIRepository extends JpaRepository<OpenAPIEntity, Long> {
}
