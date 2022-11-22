package teamproject.capstone.recipe.service.recipe;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teamproject.capstone.recipe.domain.recipe.FavoriteRecipe;
import teamproject.capstone.recipe.domain.user.User;
import teamproject.capstone.recipe.repository.recipe.FavoriteRecipeRepository;
import teamproject.capstone.recipe.repository.user.UserRepository;
import teamproject.capstone.recipe.service.user.UserService;
import teamproject.capstone.recipe.utils.values.Role;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class FavoriteRecipeServiceImplTest {
    @Autowired
    FavoriteRecipeService favoriteRecipeService;
    @Autowired
    FavoriteRecipeRepository favoriteRecipeRepository;
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
        FavoriteRecipe given = FavoriteRecipe.builder()
                .recipeSeq(28L)
                .userEmail("1@1")
                .build();

        // when
        FavoriteRecipe result = favoriteRecipeService.create(given);

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
    }

    @Test
    void findByEmailOnce() {
        // given
        User byEmail = userService.findByEmail("1@1");
        FavoriteRecipe given = FavoriteRecipe.builder()
                .recipeSeq(28L)
                .userEmail("1@1")
                .build();

        FavoriteRecipe value = favoriteRecipeService.create(given);

        // when
        List<FavoriteRecipe> result = favoriteRecipeService.findByEmail("1@1");

        // taken
        log.info("result check : {}", byEmail.getId());

        assertThat(result.get(0).getUserEmail()).isEqualTo(value.getUserEmail());
        assertThat(result.get(0).getRecipeSeq()).isEqualTo(value.getRecipeSeq());
        assertThat(result.get(0).getId()).isGreaterThan(0L);
    }

    @Test
    void findByEmailTwice() {
        // given
        User byEmail = userService.findByEmail("1@1");
        FavoriteRecipe given1 = FavoriteRecipe.builder()
                .recipeSeq(28L)
                .userEmail("1@1")
                .build();
        FavoriteRecipe given2 = FavoriteRecipe.builder()
                .recipeSeq(29L)
                .userEmail("1@1")
                .build();

        FavoriteRecipe value1 = favoriteRecipeService.create(given1);
        FavoriteRecipe value2 = favoriteRecipeService.create(given2);

        // when
        List<FavoriteRecipe> result = favoriteRecipeService.findByEmail("1@1");

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
        favoriteRecipeRepository.deleteAll();
    }
}