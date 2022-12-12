package teamproject.capstone.recipe.repository.recipe;

import com.querydsl.core.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.domain.user.User;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.repository.user.UserRepository;
import teamproject.capstone.recipe.service.recipe.FavoriteService;
import teamproject.capstone.recipe.service.recipe.OpenRecipeService;
import teamproject.capstone.recipe.service.recipe.RecipeService;
import teamproject.capstone.recipe.service.user.UserService;
import teamproject.capstone.recipe.utils.values.Role;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class FavoriteRepositoryImplTest {
    @Autowired
    OpenRecipeRepository openRecipeRepository;
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    FavoriteSimpleRepository favoriteSimpleRepository;
    @Autowired
    FavoriteRankRepository favoriteRankRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FavoriteService favoriteService;

    @BeforeEach
    void initValues() {
        User lee = User.builder().email("lee@email").name("lee").build();
        userService.create(lee);

        User kim = User.builder().email("kim@email").name("kim").build();
        userService.create(kim);

        for (int i = 0; i < 20; i++) {
            User u = User.builder().email("" + i).name("" + i).build();
            userService.create(u);
        }
    }

    @Test
    void createDuplicateDataTest() {
        User leeEmail = userService.findByEmail("lee@email");
        Optional<OpenRecipeEntity> recipeSeq1 = openRecipeRepository.findByRcpSeq(18L);

        Favorite f = Favorite.builder().recipeId(recipeSeq1.get().getId()).userEmail(leeEmail.getEmail()).build();

        FavoriteEntity favoriteEntity = favoriteService.dtoToEntity(f);
        favoriteSimpleRepository.save(favoriteEntity);
        favoriteSimpleRepository.save(favoriteEntity);
        favoriteSimpleRepository.save(favoriteEntity);
        favoriteSimpleRepository.save(favoriteEntity);
        favoriteSimpleRepository.save(favoriteEntity);

        List<Object[]> allFavorite = favoriteRepository.findAllFavorite();
        for (Object[] o : allFavorite) {
            FavoriteEntity favoriteEntity1 = (FavoriteEntity) o[0];
            OpenRecipeEntity openRecipeEntity = (OpenRecipeEntity) o[1];
            log.info("value check : {}, {}", favoriteEntity1, openRecipeEntity);
        }
    }

    @Test
    void findAllTest() {
        // given
        User leeEmail = userService.findByEmail("lee@email");
        User kimEmail = userService.findByEmail("kim@email");

        Optional<OpenRecipeEntity> recipeSeq1 = openRecipeRepository.findByRcpSeq(18L);
        Optional<OpenRecipeEntity> recipeSeq2 = openRecipeRepository.findByRcpSeq(19L);
        Optional<OpenRecipeEntity> recipeSeq3 = openRecipeRepository.findByRcpSeq(28L);

        // when
        if (recipeSeq1.isPresent()) {
            FavoriteEntity build = FavoriteEntity.builder().recipe(recipeSeq1.get()).userEmail(leeEmail.getEmail()).build();
            favoriteSimpleRepository.save(build);
        }
        if (recipeSeq1.isPresent()) {
            FavoriteEntity build = FavoriteEntity.builder().recipe(recipeSeq1.get()).userEmail(kimEmail.getEmail()).build();
            favoriteSimpleRepository.save(build);
        }
        if (recipeSeq2.isPresent()) {
            FavoriteEntity build = FavoriteEntity.builder().recipe(recipeSeq2.get()).userEmail(leeEmail.getEmail()).build();
            favoriteSimpleRepository.save(build);
        }
        if (recipeSeq3.isPresent()) {
            FavoriteEntity build = FavoriteEntity.builder().recipe(recipeSeq3.get()).userEmail(leeEmail.getEmail()).build();
            favoriteSimpleRepository.save(build);
        }

        List<Object[]> allFavorite = favoriteRepository.findAllFavorite();

        // then
        for (Object[] val : allFavorite) {
            FavoriteEntity favoriteEntity = (FavoriteEntity) val[0];
            OpenRecipeEntity openRecipeEntity = (OpenRecipeEntity) val[1];

            if (favoriteEntity.getUserEmail().equals(leeEmail.getEmail())) {
                assertThat(favoriteEntity.getUserEmail()).isEqualTo(leeEmail.getEmail());
            } else {
                assertThat(favoriteEntity.getUserEmail()).isEqualTo(kimEmail.getEmail());
            }
            if (openRecipeEntity.getRcpSeq().equals(recipeSeq1.get().getRcpSeq())) {
                assertThat(openRecipeEntity.getRcpNm()).isEqualTo(recipeSeq1.get().getRcpNm());
            } else if (openRecipeEntity.getRcpSeq().equals(recipeSeq2.get().getRcpSeq())) {
                assertThat(openRecipeEntity.getRcpNm()).isEqualTo(recipeSeq2.get().getRcpNm());
            } else {
                assertThat(openRecipeEntity.getRcpNm()).isEqualTo(recipeSeq3.get().getRcpNm());
            }
        }
    }

    @Test
    void findRecipeSeqAndEmailTest() {
        // given
        User leeEmail = userService.findByEmail("lee@email");
        Optional<OpenRecipeEntity> recipeSeq1 = openRecipeRepository.findByRcpSeq(18L);

        if (recipeSeq1.isPresent()) {
            FavoriteEntity build = FavoriteEntity.builder().userEmail(leeEmail.getEmail()).recipe(recipeSeq1.get()).build();
            favoriteSimpleRepository.save(build);
        }

        // when
        Object[] valueFound = favoriteRepository.findFavoriteByRecipeSeqAndEmail(recipeSeq1.get().getRcpSeq(), leeEmail.getEmail());

        // then
        assertThat(((FavoriteEntity) valueFound[0]).getUserEmail()).isEqualTo(leeEmail.getEmail());
        assertThat(((OpenRecipeEntity) valueFound[1]).getRcpSeq()).isEqualTo(recipeSeq1.get().getRcpSeq());
    }

    @Test
    void findRecipeSeq() {
        // given
        User leeEmail = userService.findByEmail("lee@email");
        User kimEmail = userService.findByEmail("kim@email");
        Optional<OpenRecipeEntity> recipeSeq1 = openRecipeRepository.findByRcpSeq(18L);

        FavoriteEntity build1 = FavoriteEntity.builder().userEmail(leeEmail.getEmail()).recipe(recipeSeq1.get()).build();
        FavoriteEntity build2 = FavoriteEntity.builder().userEmail(kimEmail.getEmail()).recipe(recipeSeq1.get()).build();
        favoriteSimpleRepository.save(build1);
        favoriteSimpleRepository.save(build2);

        // when
        List<Object[]> valueFound = favoriteRepository.findFavoriteByRecipeSeq(18L);

        // then
        for (Object[] val : valueFound) {
            FavoriteEntity favoriteEntity = (FavoriteEntity) val[0];
            OpenRecipeEntity openRecipeEntity = (OpenRecipeEntity) val[1];

            if (favoriteEntity.getUserEmail().equals(leeEmail.getEmail())) {
                assertThat(favoriteEntity.getUserEmail()).isEqualTo(leeEmail.getEmail());
            } else {
                assertThat(favoriteEntity.getUserEmail()).isEqualTo(kimEmail.getEmail());
            }
            assertThat(openRecipeEntity.getRcpNm()).isEqualTo(recipeSeq1.get().getRcpNm());
        }
    }

    @Test
    void findEmail() {
        // given
        User leeEmail = userService.findByEmail("lee@email");
        Optional<OpenRecipeEntity> recipeSeq1 = openRecipeRepository.findByRcpSeq(18L);
        Optional<OpenRecipeEntity> recipeSeq2 = openRecipeRepository.findByRcpSeq(19L);

        FavoriteEntity build1 = FavoriteEntity.builder().userEmail(leeEmail.getEmail()).recipe(recipeSeq1.get()).build();
        FavoriteEntity build2 = FavoriteEntity.builder().userEmail(leeEmail.getEmail()).recipe(recipeSeq2.get()).build();
        favoriteSimpleRepository.save(build1);
        favoriteSimpleRepository.save(build2);

        // when
        List<Object[]> valueFound = favoriteRepository.findFavoriteByEmail(leeEmail.getEmail());

        // then
        for (Object[] val : valueFound) {
            FavoriteEntity favoriteEntity = (FavoriteEntity) val[0];
            OpenRecipeEntity openRecipeEntity = (OpenRecipeEntity) val[1];

            if (favoriteEntity.getUserEmail().equals(leeEmail.getEmail())) {
                assertThat(favoriteEntity.getUserEmail()).isEqualTo(leeEmail.getEmail());
            }
            if (openRecipeEntity.getRcpSeq().equals(recipeSeq1.get().getRcpSeq())) {
                assertThat(openRecipeEntity.getRcpNm()).isEqualTo(recipeSeq1.get().getRcpNm());
            } else {
                assertThat(openRecipeEntity.getRcpNm()).isEqualTo(recipeSeq2.get().getRcpNm());
            }
        }
    }

    @Test
    void findEmailIfManySameSeqSaved() {
        // given
        User leeEmail = userService.findByEmail("lee@email");
        Optional<OpenRecipeEntity> recipeSeq1 = openRecipeRepository.findByRcpSeq(18L);
        Optional<OpenRecipeEntity> recipeSeq2 = openRecipeRepository.findByRcpSeq(19L);

        FavoriteEntity build1 = FavoriteEntity.builder().userEmail(leeEmail.getEmail()).recipe(recipeSeq1.get()).build();
        FavoriteEntity build2 = FavoriteEntity.builder().userEmail(leeEmail.getEmail()).recipe(recipeSeq2.get()).build();
        FavoriteEntity save1 = favoriteSimpleRepository.save(build1);
        FavoriteEntity save2 = favoriteSimpleRepository.save(build2);

        // when
        List<Object[]> valueFound = favoriteRepository.findFavoriteByEmail(leeEmail.getEmail());

        // then
        for (Object[] val : valueFound) {
            FavoriteEntity favoriteEntity = (FavoriteEntity) val[0];
            OpenRecipeEntity openRecipeEntity = (OpenRecipeEntity) val[1];

            if (favoriteEntity.getUserEmail().equals(leeEmail.getEmail())) {
                assertThat(favoriteEntity.getUserEmail()).isEqualTo(leeEmail.getEmail());
            }
            if (openRecipeEntity.getRcpSeq().equals(recipeSeq1.get().getRcpSeq())) {
                assertThat(openRecipeEntity.getRcpNm()).isEqualTo(recipeSeq1.get().getRcpNm());
            } else {
                assertThat(openRecipeEntity.getRcpNm()).isEqualTo(recipeSeq2.get().getRcpNm());
            }
        }
    }

    @Test
    void findNoRecipeSeqAndEmailTest() {
        // given
        Long wrong = 0L;
        String email = "justEmail";

        // when
        Object[] value = favoriteRepository.findFavoriteByRecipeSeqAndEmail(wrong, email);

        if (value != null) {
            FavoriteEntity favoriteEntity = (FavoriteEntity) value[0];
            OpenRecipeEntity openRecipeEntity = (OpenRecipeEntity) value[1];
            log.info("favorite find check : {}, {}", favoriteEntity, openRecipeEntity);
        }

        //then
        assertThat(value).isNull();
    }

    @Test
    void findNoAllTest() {
        // given, when
        List<Object[]> allFavorite = favoriteRepository.findAllFavorite();

        if (!allFavorite.isEmpty()) {
            for (Object[] value : allFavorite) {
                FavoriteEntity favoriteEntity = (FavoriteEntity) value[0];
                OpenRecipeEntity openRecipeEntity = (OpenRecipeEntity) value[1];
                log.info("favorite find check : {}, {}", favoriteEntity, openRecipeEntity);
            }
        }

        //then
        assertThat(allFavorite).isEmpty();
    }

    @Test
    void littleMostFavoriteValueTest() {
        // given
        User leeEmail = userService.findByEmail("lee@email");
        User kimEmail = userService.findByEmail("kim@email");
        Optional<OpenRecipeEntity> recipeSeq1 = openRecipeRepository.findByRcpSeq(18L);
        Optional<OpenRecipeEntity> recipeSeq2 = openRecipeRepository.findByRcpSeq(19L);

        FavoriteEntity build1 = FavoriteEntity.builder().userEmail(leeEmail.getEmail()).recipe(recipeSeq1.get()).build();
        FavoriteEntity build11 = FavoriteEntity.builder().userEmail(leeEmail.getEmail()).recipe(recipeSeq2.get()).build();
        FavoriteEntity build2 = FavoriteEntity.builder().userEmail(kimEmail.getEmail()).recipe(recipeSeq2.get()).build();
        FavoriteEntity save1 = favoriteSimpleRepository.save(build1);
        FavoriteEntity save11 = favoriteSimpleRepository.save(build11);
        FavoriteEntity save2 = favoriteSimpleRepository.save(build2);

        // when
        List<Object[]> allRankFavoriteRecipe = favoriteRankRepository.findWithRankFavoriteRecipe();

        // then
        if (!allRankFavoriteRecipe.isEmpty()) {
            Object[] val1 = allRankFavoriteRecipe.get(0);

            FavoriteEntity favoriteEntity1 = (FavoriteEntity) val1[0];
            OpenRecipeEntity openRecipeEntity1 = (OpenRecipeEntity) val1[1];
            Long count = (Long) val1[2];

            assertThat(openRecipeEntity1.getRcpSeq()).isEqualTo(recipeSeq2.get().getRcpSeq());
            assertThat(count).isEqualTo(2);

            Object[] val2 = allRankFavoriteRecipe.get(1);

            FavoriteEntity favoriteEntity2 = (FavoriteEntity) val2[0];
            OpenRecipeEntity openRecipeEntity2 = (OpenRecipeEntity) val2[1];
            Long count2 = (Long) val2[2];

            assertThat(openRecipeEntity2.getRcpSeq()).isEqualTo(recipeSeq1.get().getRcpSeq());
            assertThat(count2).isEqualTo(1);
        }
    }

    @Test
    void manyMostFavoriteValueTest() {
        // given
        Long def = 215L;

        for (int i = 0; i < 15; i++) {
            User u = userService.findByEmail("" + i);
            Optional<OpenRecipeEntity> or = openRecipeRepository.findByRcpSeq(def + i);
            FavoriteEntity build = FavoriteEntity.builder().userEmail(u.getEmail()).recipe(or.get()).build();
            favoriteSimpleRepository.save(build);
        }

        for (int i = 0; i < 8; i++) {
            User u = userService.findByEmail("" + i);
            Optional<OpenRecipeEntity> or = openRecipeRepository.findByRcpSeq(def + i);
            FavoriteEntity build = FavoriteEntity.builder().userEmail(u.getEmail()).recipe(or.get()).build();
            favoriteSimpleRepository.save(build);
        }

        int a = 0;
        for (int i = 15; i < 20; i++) {
            User u = userService.findByEmail("" + i);
            Optional<OpenRecipeEntity> or = openRecipeRepository.findByRcpSeq(def + a);
            FavoriteEntity build = FavoriteEntity.builder().userEmail(u.getEmail()).recipe(or.get()).build();
            favoriteSimpleRepository.save(build);

            a++;
        }

        // when
        List<Object[]> allRankFavoriteRecipe = favoriteRankRepository.findWithRankFavoriteRecipe();

        // then
        if (!allRankFavoriteRecipe.isEmpty()) {
            Long i = 0L;
            for (Object[] val : allRankFavoriteRecipe) {
                FavoriteEntity favoriteEntity = (FavoriteEntity) val[0];
                OpenRecipeEntity openRecipeEntity = (OpenRecipeEntity) val[1];
                Long count = (Long) val[2];

                assertThat(openRecipeEntity.getRcpSeq()).isEqualTo(def + i);
                log.info("count value check with seq : {} - {}", openRecipeEntity.getRcpSeq(), count);

                i++;
            }

            assertThat(allRankFavoriteRecipe.size()).isEqualTo(8);
        }
    }

    @Test
    void usersFavoriteValueTest() {
    }

    @AfterEach
    void deleteTest() {
        userRepository.deleteAll();
        favoriteSimpleRepository.deleteAll();
    }
}