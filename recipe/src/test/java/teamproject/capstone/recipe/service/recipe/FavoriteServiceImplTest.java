package teamproject.capstone.recipe.service.recipe;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.domain.user.User;
import teamproject.capstone.recipe.repository.recipe.FavoriteSimpleRepository;
import teamproject.capstone.recipe.repository.user.UserRepository;
import teamproject.capstone.recipe.service.user.UserService;
import teamproject.capstone.recipe.utils.values.Role;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class FavoriteServiceImplTest {
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    FavoriteSimpleRepository favoriteSimpleRepository;
    @Autowired
    OpenRecipeService openRecipeService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void genUser() {
        userService.create(User.builder()
                        .email("1@1")
                        .role(Role.USER)
                        .name("1")
                        .Uid("")
                .build());

        userService.create(User.builder()
                .email("123456@naver.com")
                .role(Role.USER)
                .name("123456")
                .Uid("")
                .build());
    }

    @Test
    void create() {
        // given
        User byEmail = userService.findByEmail("1@1");
        OpenRecipe byRecipeSeq = openRecipeService.findByRecipeSeq(28L);

        Favorite given = Favorite.builder()
                .id(null)
                .recipeId(byRecipeSeq.getId())
                .recipeSeq(byRecipeSeq.getRcpSeq())
                .userEmail("1@1")
                .build();

        // when
        Favorite result = favoriteService.create(given);

        // taken
        log.info("result check : {}", result.getId());

        assertThat(byEmail.getEmail()).isEqualTo(result.getUserEmail());
        assertThat(given.getRecipeSeq()).isEqualTo(result.getRecipeSeq());
    }

    @Test
    void createAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteByEmail() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void findAll() {
        // given
        User byEmail = userService.findByEmail("1@1");
        OpenRecipe byRecipeSeq1 = openRecipeService.findByRecipeSeq(18L);
        OpenRecipe byRecipeSeq2 = openRecipeService.findByRecipeSeq(28L);

        Favorite given1 = Favorite.builder()
                .id(null)
                .recipeId(byRecipeSeq1.getId())
                .recipeSeq(byRecipeSeq1.getRcpSeq())
                .userEmail("1@1")
                .build();

        Favorite given2 = Favorite.builder()
                .id(null)
                .recipeId(byRecipeSeq2.getId())
                .recipeSeq(byRecipeSeq2.getRcpSeq())
                .userEmail("1@1")
                .build();

        // when
        Favorite value1 = favoriteService.create(given1);
        Favorite value2 = favoriteService.create(given2);
        List<Favorite> result = favoriteService.findAll();

        // taken
        log.info("result check : {}", result);

        assertThat(result.get(0).getUserEmail()).isEqualTo(value1.getUserEmail());
        assertThat(result.get(0).getRecipeSeq()).isEqualTo(value1.getRecipeSeq());
        assertThat(result.get(0).getId()).isGreaterThan(0L);
        assertThat(result.get(1).getUserEmail()).isEqualTo(value2.getUserEmail());
        assertThat(result.get(1).getRecipeSeq()).isEqualTo(value2.getRecipeSeq());
        assertThat(result.get(1).getId()).isGreaterThan(0L);
    }

    @Test
    void findByEmailOnce() {
        // given
        User byEmail = userService.findByEmail("1@1");
        OpenRecipe byRecipeSeq = openRecipeService.findByRecipeSeq(28L);

        Favorite given = Favorite.builder()
                .id(null)
                .recipeId(byRecipeSeq.getId())
                .recipeSeq(byRecipeSeq.getRcpSeq())
                .userEmail("1@1")
                .build();

        // when
        Favorite value = favoriteService.create(given);
        List<Favorite> result = favoriteService.findByEmail("1@1");

        // taken
        log.info("result check : {}", result);

        assertThat(result.get(0).getUserEmail()).isEqualTo(value.getUserEmail());
        assertThat(result.get(0).getRecipeSeq()).isEqualTo(value.getRecipeSeq());
        assertThat(result.get(0).getId()).isGreaterThan(0L);
    }

    @Test
    void findByEmailTwice() {
        // given
        User byEmail = userService.findByEmail("1@1");
        OpenRecipe byRecipeSeq1 = openRecipeService.findByRecipeSeq(28L);
        OpenRecipe byRecipeSeq2 = openRecipeService.findByRecipeSeq(28L);

        Favorite given1 = Favorite.builder()
                .id(null)
                .recipeId(byRecipeSeq1.getId())
                .recipeSeq(byRecipeSeq1.getRcpSeq())
                .userEmail("1@1")
                .build();

        Favorite given2 = Favorite.builder()
                .id(null)
                .recipeId(byRecipeSeq2.getId())
                .recipeSeq(byRecipeSeq2.getRcpSeq())
                .userEmail("1@1")
                .build();

        Favorite value1 = favoriteService.create(given1);
        Favorite value2 = favoriteService.create(given2);

        // when
        List<Favorite> result = favoriteService.findByEmail("1@1");

        // taken
        log.info("result check : {}", byEmail.getId());

        assertThat(result.get(0).getUserEmail()).isEqualTo(value1.getUserEmail());
        assertThat(result.get(0).getRecipeSeq()).isEqualTo(value1.getRecipeSeq());
        assertThat(result.get(0).getId()).isGreaterThan(0L);

        assertThat(result.get(1).getUserEmail()).isEqualTo(value2.getUserEmail());
        assertThat(result.get(1).getRecipeSeq()).isEqualTo(value2.getRecipeSeq());
        assertThat(result.get(1).getId()).isGreaterThan(0L);
    }

    @AfterEach
    void deleteEach () {
        userRepository.deleteAll();
        favoriteSimpleRepository.deleteAll();
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findBySeq() {
    }

    @Test
    void mostFavoriteRankRecipe() {
    }
}