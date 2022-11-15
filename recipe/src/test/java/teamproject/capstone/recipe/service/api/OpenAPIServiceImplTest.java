package teamproject.capstone.recipe.service.api;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.utils.api.APISearch;
import teamproject.capstone.recipe.utils.api.json.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.api.OpenRecipe;
import teamproject.capstone.recipe.utils.api.json.Row;
import teamproject.capstone.recipe.entity.api.OpenRecipeEntity;
import teamproject.capstone.recipe.repository.api.OpenAPIRepository;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.api.OpenAPIDelegator;
import teamproject.capstone.recipe.utils.api.OpenAPIHandler;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;
import teamproject.capstone.recipe.utils.values.SearchType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class OpenAPIServiceImplTest {
    @Autowired
    OpenAPIHandler openAPIHandler;
    @Autowired
    OpenAPIRepository openAPIRepository;

    @Autowired
    OpenAPIService openAPIService;
    @Autowired
    OpenAPIPageService openAPIPageService;
    @Autowired
    OpenAPISearchService openAPISearchService;

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
    void openAPISearchAndOneSearch() {
        // given
        List<APISearch> v = new ArrayList<>();
        v.add(APISearch.builder().keyword("찌기").type("way").build());

        PageRequest of = PageRequest.of(0, 10);

        // when
        APIPageResult<OpenRecipe, OpenRecipeEntity> api = openAPISearchService.searchAndAPIDataSources(v, of);

        // then
        log.info("api test check : {}", api);
    }

    @Test
    void openAPISearchOrHandling() {
        // given
        List<List<APISearch>> lists = fiveCase();

        for (List<APISearch> valueTest : lists) {
            PageRequest of = PageRequest.of(0, 100);

            // when
            APIPageResult<OpenRecipe, OpenRecipeEntity> orAPIPageResult = openAPISearchService.searchOrAPIDataSources(valueTest, of);

            // then
            for (APISearch s : valueTest) {

                if (orAPIPageResult.getDtoList().isEmpty()) {
                    continue;
                } else {
                    OpenRecipe testVal = orAPIPageResult.getDtoList().get(0);
                    if (testVal != null) {
                        if (s.getType().equals(SearchType.RECIPE_DETAILS.getValue())) {
                            assertThat(s.getKeyword()).isIn(orAPIPageResult.getDtoList().get(0).getRcpPartsDtls());
                        }
                        if (s.getType().equals(SearchType.RECIPE_PARTS.getValue())) {
                            assertThat(s.getKeyword()).isEqualTo(orAPIPageResult.getDtoList().get(0).getRcpPat2());
                        }
                        if (s.getType().equals(SearchType.RECIPE_SEQUENCE.getValue())) {
                            assertThat(Long.parseLong(s.getKeyword())).isEqualTo(orAPIPageResult.getDtoList().get(0).getRcpSeq());
                        }
                        if (s.getType().equals(SearchType.RECIPE_WAY.getValue())) {
                            assertThat(s.getKeyword()).isEqualTo(orAPIPageResult.getDtoList().get(0).getRcpWay2());
                        }
                    }
                }
            }
        }
    }

    @Test
    void openAPISearchAndHandling() {
        // given
        List<List<APISearch>> lists = fiveCase();
        for (List<APISearch> vs : lists) {
            int count = 1;
            for (APISearch v : vs) {
                log.info("value of api search list : index - {}, ket - {}, type - {}", count, v.getKeyword(), v.getType());
                count++;
            }
        }

        for (List<APISearch> valueTest : lists) {
            PageRequest of = PageRequest.of(0, 100);

            // when
            APIPageResult<OpenRecipe, OpenRecipeEntity> andAPIPageResult = openAPISearchService.searchAndAPIDataSources(valueTest, of);

            // then
            for (APISearch s : valueTest) {

                if (andAPIPageResult.getDtoList().isEmpty()) {
                    continue;
                } else {
                    OpenRecipe testVal = andAPIPageResult.getDtoList().get(0);
                    if (testVal != null) {
                        if (s.getType().equals(SearchType.RECIPE_DETAILS.getValue())) {
                            assertThat(s.getKeyword()).isIn(andAPIPageResult.getDtoList().get(0).getRcpPartsDtls());
                        }
                        if (s.getType().equals(SearchType.RECIPE_PARTS.getValue())) {
                            assertThat(s.getKeyword()).isEqualTo(andAPIPageResult.getDtoList().get(0).getRcpPat2());
                        }
                        if (s.getType().equals(SearchType.RECIPE_SEQUENCE.getValue())) {
                            assertThat(Long.parseLong(s.getKeyword())).isEqualTo(andAPIPageResult.getDtoList().get(0).getRcpSeq());
                        }
                        if (s.getType().equals(SearchType.RECIPE_WAY.getValue())) {
                            assertThat(s.getKeyword()).isEqualTo(andAPIPageResult.getDtoList().get(0).getRcpWay2());
                        }
                    }
                }
            }
        }
    }

    private List<List<APISearch>> fiveCase() {
        List<List<APISearch>> result = new ArrayList<>();
        List<APISearch> testSearch = new ArrayList<>();

        testSearch.add(APISearch.builder().keyword("배추").type(SearchType.RECIPE_DETAILS.getValue()).build());
        result.add(testSearch);
        testSearch.clear();
        testSearch.add(APISearch.builder().keyword("반찬").type(SearchType.RECIPE_PARTS.getValue()).build());
        result.add(testSearch);
        testSearch.clear();
        testSearch.add(APISearch.builder().keyword("찌기").type(SearchType.RECIPE_WAY.getValue()).build());
        result.add(testSearch);
        testSearch.clear();
        testSearch.add(APISearch.builder().keyword("28").type(SearchType.RECIPE_SEQUENCE.getValue()).build());
        result.add(testSearch);
        testSearch.clear();

        testSearch.add(APISearch.builder().keyword("배추").type(SearchType.RECIPE_DETAILS.getValue()).build());
        testSearch.add(APISearch.builder().keyword("반찬").type(SearchType.RECIPE_PARTS.getValue()).build());
        testSearch.add(APISearch.builder().keyword("찌기").type(SearchType.RECIPE_WAY.getValue()).build());
        testSearch.add(APISearch.builder().keyword("28").type(SearchType.RECIPE_SEQUENCE.getValue()).build());

        for (int i = 2; i < 5; i++) {
            List<List<APISearch>> collect = Generator.combination(testSearch)
                    .simple(i)
                    .stream()
                    .collect(Collectors.toList());

            result.addAll(collect);
        }

        return result;
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