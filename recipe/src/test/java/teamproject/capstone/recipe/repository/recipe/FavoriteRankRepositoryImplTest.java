package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class FavoriteRankRepositoryImplTest {
    @Autowired
    FavoriteRankRepository favoriteRankRepository;

    @Test
    void findWithRankFavoriteRecipe() {
        List<Tuple> withRankFavoriteRecipe = favoriteRankRepository.findWithRankFavoriteRecipe();

        log.info("value check of tuple : {}", withRankFavoriteRecipe.toString());
    }
}