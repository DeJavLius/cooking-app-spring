package teamproject.capstone.recipe.repository.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.domain.api.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.domain.api.Row;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.APIPageResult;
import teamproject.capstone.recipe.utils.OpenAPISerializer;
import teamproject.capstone.recipe.utils.api.OpenAPIHandler;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PageOpenAPIRepositoryImplTest {
    @Autowired
    OpenAPIHandler openAPIHandler;
    @Autowired
    OpenAPIRepository openAPIRepository;
    @Autowired
    PageOpenAPIRepository pageOpenAPIRepository;

    @BeforeEach
    void pageInsertionBefore() {
        List<OpenAPIRecipe> fetchValues = openAPIHandler.requestAllOpenAPI();
        List<OpenRecipeEntity> forInsert = new ArrayList<>();
        for (OpenAPIRecipe oap: fetchValues) {
            for (Row r : oap.getRow()) {
                OpenRecipe op = OpenAPISerializer.rowToOpenRecipe(r);
                OpenRecipeEntity ope = OpenRecipeConverter.dtoToEntity(op);
                forInsert.add(ope);
            }
            openAPIRepository.saveAll(forInsert);
            forInsert.clear();
        }
    }

    @Test
    void openAPIPageHandling() {
        // given
        PageRequest of = PageRequest.of(0, 10);
        List<OpenRecipeEntity> allFound = openAPIRepository.findAll();

        // when
        Page<OpenRecipeEntity> openRecipeEntities = pageOpenAPIRepository.openAPIPageHandling(of);
        Function<OpenRecipeEntity, OpenRecipe> fn = (OpenRecipeConverter::entityToDto);
        APIPageResult<OpenRecipe, OpenRecipeEntity> orPageResult = new APIPageResult<>(openRecipeEntities, fn);

        // then
        log.info("test of page : {}", orPageResult.getDtoList());
        log.info("test of page : {}", orPageResult.getDtoList().size());
        log.info("test of page : {}", orPageResult.getPageList());
    }

    @AfterEach
    void deleteAllInsertValue() {
        openAPIRepository.deleteAll();
    }
}