package teamproject.capstone.recipe.service.api;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.utils.api.json.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.utils.api.json.Row;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.repository.api.OpenAPIRepository;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.api.OpenAPIDelegator;
import teamproject.capstone.recipe.utils.api.OpenAPIHandler;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OpenAPIServiceImplTest {
    @Autowired
    OpenAPIHandler openAPIHandler;
    @Autowired
    OpenAPIRepository openAPIRepository;

    @Autowired
    OpenAPIService openAPIService;
    @Autowired
    OpenAPIServiceImpl openAPIServiceImpl;
    @Autowired
    OpenAPIPageService openAPIPageService;

    @Before("allAPIDataSources")
    void pageInsertionBefore() {
        List<OpenAPIRecipe> fetchValues = openAPIHandler.requestAllOpenAPI();
        List<OpenRecipeEntity> forInsert = new ArrayList<>();
        for (OpenAPIRecipe oap: fetchValues) {
            for (Row r : oap.getRow()) {
                OpenRecipe op = OpenAPIDelegator.rowToOpenRecipe(r);
                OpenRecipeEntity ope = OpenRecipeConverter.dtoToEntity(op);
                forInsert.add(ope);
            }
            openAPIRepository.saveAll(forInsert);
            forInsert.clear();
        }
    }

    @Test
    void allAPIDataSources() {
        // given
        PageRequest pr = PageRequest.of(0, 10);

        // when
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openAPIPageService.allAPIDataSources(pr);

        // then
        assertThat(openRecipeAPIPageResult.getDtoList().size()).isEqualTo(pr.getPageSize());
        assertThat(openRecipeAPIPageResult.getCurrentPage()).isEqualTo(1);
    }

    @Test
    void create() {
    }

    @Test
    void createAll() {
    }

    @AfterEach
    void deleteAllInsertValue() {
        openAPIRepository.deleteAll();
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }
}