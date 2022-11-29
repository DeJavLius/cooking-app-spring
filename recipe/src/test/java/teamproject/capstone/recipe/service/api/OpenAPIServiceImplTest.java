package teamproject.capstone.recipe.service.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.utils.page.Search;
import teamproject.capstone.recipe.utils.api.json.parts.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.utils.api.json.parts.Row;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.repository.recipe.OpenRecipeRepository;
import teamproject.capstone.recipe.utils.page.PageResult;
import teamproject.capstone.recipe.utils.api.openApi.OpenAPIDelegator;
import teamproject.capstone.recipe.utils.api.openApi.OpenAPIHandler;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;
import teamproject.capstone.recipe.utils.values.SearchType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class OpenAPIServiceImplTest {
    @Autowired
    OpenAPIHandler openAPIHandler;
    @Autowired
    OpenRecipeRepository openRecipeRepository;

    @Autowired
    OpenAPIService openAPIService;
    @Autowired
    OpenAPIPageService openAPIPageService;
    @Autowired
    OpenAPISearchService openAPISearchService;

    @BeforeEach
    void pageInsertionBefore() {
        List<OpenAPIRecipe> fetchValues = openAPIHandler.requestAllOpenAPI();
        List<OpenRecipeEntity> forInsert = new ArrayList<>();
        for (OpenAPIRecipe oap: fetchValues) {
            for (Row r : oap.getRow()) {
                OpenRecipe op = OpenAPIDelegator.rowToOpenRecipe(r);
                OpenRecipeEntity ope = OpenRecipeConverter.dtoToEntity(op);
                forInsert.add(ope);
            }
            openRecipeRepository.saveAll(forInsert);
            forInsert.clear();
        }
    }

    @Test
    void allAPIDataSources() {

        // given
        PageRequest pr = PageRequest.of(0, 10);

        // when
        PageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openAPIPageService.allAPIDataSources(pr);

        // then
        assertThat(openRecipeAPIPageResult.getDtoList().size()).isEqualTo(10);
        assertThat(openRecipeAPIPageResult.getCurrentPage()).isEqualTo(1);
    }

    @Test
    void openAPISearchAndOneSearch() {
        // given
        List<Search> v = new ArrayList<>();
        v.add(Search.builder().keyword("찌기").type("way").build());

        PageRequest of = PageRequest.of(0, 10);

        // when
        PageResult<OpenRecipe, OpenRecipeEntity> api = openAPISearchService.searchAndAPIDataSources(v, of);

        // then
        log.info("api test check : {}", api);
    }

    @Test
    void openAPISearchOrHandling() {
        // given
        List<List<Search>> lists = fiveCase();

        for (List<Search> valueTest : lists) {
            PageRequest of = PageRequest.of(0, 100);

            // when
            PageResult<OpenRecipe, OpenRecipeEntity> orAPIPageResult = openAPISearchService.searchOrAPIDataSources(valueTest, of);

            // then
            int count = 0;
            for (Search s : valueTest) {
                if (orAPIPageResult.getDtoList().isEmpty()) {
                    continue;
                } else {
                    OpenRecipe testVal = orAPIPageResult.getDtoList().get(0);
                    if (testVal != null) {
                        if (s.getType().equals(SearchType.RECIPE_NAME.getValue())) {
                            if (orAPIPageResult.getDtoList().get(0).getRcpNm().contains(s.getKeyword())) count++;
                        }
                        if (s.getType().equals(SearchType.RECIPE_DETAILS.getValue())) {
                            if (orAPIPageResult.getDtoList().get(0).getRcpPartsDtls().contains(s.getKeyword())) count++;
                        }
                        if (s.getType().equals(SearchType.RECIPE_PARTS.getValue())) {
                            if (orAPIPageResult.getDtoList().get(0).getRcpPat2().contains(s.getKeyword())) count++;
                        }
                        if (s.getType().equals(SearchType.RECIPE_SEQUENCE.getValue())) {
                            if (orAPIPageResult.getDtoList().get(0).getRcpSeq().toString().equals(s.getKeyword())) count++;
                        }
                        if (s.getType().equals(SearchType.RECIPE_WAY.getValue())) {
                            if (orAPIPageResult.getDtoList().get(0).getRcpWay2().contains(s.getKeyword())) count++;
                        }
                    }
                }
            }

            if (orAPIPageResult.getDtoList().isEmpty()) {
                assertThat(count).isEqualTo(0);
            } else {
                assertThat(count).isGreaterThan(0);
            }
        }
    }

    @Test
    void openAPISearchAndHandling() {
        // given
        List<List<Search>> lists = fiveCase();

        for (List<Search> valueTest : lists) {
            PageRequest of = PageRequest.of(0, 100);

            // when
            PageResult<OpenRecipe, OpenRecipeEntity> andAPIPageResult = openAPISearchService.searchAndAPIDataSources(valueTest, of);

            // then
            for (Search s : valueTest) {

                if (andAPIPageResult.getDtoList().isEmpty()) {
                    continue;
                } else {
                    OpenRecipe testVal = andAPIPageResult.getDtoList().get(0);
                    if (testVal != null) {
                        if (s.getType().equals(SearchType.RECIPE_NAME.getValue())) {
                            assertThat(andAPIPageResult.getDtoList().get(0).getRcpNm()).contains(s.getKeyword());
                        }
                        if (s.getType().equals(SearchType.RECIPE_DETAILS.getValue())) {
                            assertThat(andAPIPageResult.getDtoList().get(0).getRcpPartsDtls()).contains(s.getKeyword());
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

    private List<List<Search>> fiveCase() {
        List<List<Search>> result = new ArrayList<>();
        List<Search> testSearch = new ArrayList<>();

        testSearch.add(Search.builder().keyword("새우").type(SearchType.RECIPE_NAME.getValue()).build());
        testSearch.add(Search.builder().keyword("배추").type(SearchType.RECIPE_DETAILS.getValue()).build());
        testSearch.add(Search.builder().keyword("반찬").type(SearchType.RECIPE_PARTS.getValue()).build());
        testSearch.add(Search.builder().keyword("찌기").type(SearchType.RECIPE_WAY.getValue()).build());
        testSearch.add(Search.builder().keyword("28").type(SearchType.RECIPE_SEQUENCE.getValue()).build());

        result.add(testSearch);

        for (int i = 2; i < 6; i++) {
            List<List<Search>> collect = Generator.combination(testSearch)
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
        openRecipeRepository.deleteAll();
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }
}