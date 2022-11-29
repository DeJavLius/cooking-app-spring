package teamproject.capstone.recipe.repository.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.repository.recipe.OpenRecipePageWithSearchRepository;
import teamproject.capstone.recipe.repository.recipe.OpenRecipeRepository;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.page.Search;
import teamproject.capstone.recipe.utils.api.json.parts.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.utils.api.json.parts.Row;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.openApi.OpenAPIDelegator;
import teamproject.capstone.recipe.utils.api.openApi.OpenAPIHandler;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;
import teamproject.capstone.recipe.utils.values.SearchType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class OpenPageDetailRequestWithSearchRepositoryImplTest {
    @Autowired
    OpenAPIHandler openAPIHandler;
    @Autowired
    OpenRecipeRepository openRecipeRepository;
    @Autowired
    OpenRecipePageWithSearchRepository openRecipePageWithSearchRepository;

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
    void openAPIPageHandling() {
        for (int i = 1; i <= 100; i++) {
            // given
            PageRequest of = PageRequest.of(0, i);

            // when
            Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPIPageHandling(of);
            Function<OpenRecipeEntity, OpenRecipe> fn = (OpenRecipeConverter::entityToDto);
            APIPageResult<OpenRecipe, OpenRecipeEntity> orPageResult = new APIPageResult<>(openRecipeEntities, fn);

            int i1 = 0;
            try {
                i1 = (1058 / i) + (1058 % i > 0 ? 1 : 0);
            } catch (Exception e) {
                log.info("wrong value, can't division value : {}", i1);

                throw new IllegalStateException();
            }

            // then
            log.info("currentPage : {}", orPageResult.getNowPage());
            assertThat(orPageResult.getNowPage()).isEqualTo(1);
            log.info("totalPage : {}, test : {}", orPageResult.getTotalPage(), i1);
            assertThat(orPageResult.getTotalPage()).isEqualTo(i1);
            log.info("each page size : {}, test : {}", orPageResult.getDtoList().size(), i);
            assertThat(orPageResult.getDtoList().size()).isEqualTo(i);
        }
    }

    @Test
    void openAPISearchOrHandling() {
        // given
        List<List<Search>> lists = fiveCase();
        int total = 1058;

        for (List<Search> valueTest : lists) {

            log.info("value test check : {}", valueTest);

            PageRequest of = PageRequest.of(0, 100);

            // when
            Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchOrPageHandling(valueTest, of);
            Function<OpenRecipeEntity, OpenRecipe> fn = (OpenRecipeConverter::entityToDto);
            APIPageResult<OpenRecipe, OpenRecipeEntity> orPageResult = new APIPageResult<>(openRecipeEntities, fn);

            total = openRecipeEntities.getTotalPages();

            // then
//                log.info("search value check of call : {}", orPageResult);

            int count = 0;
            for (Search s : valueTest) {
                log.info("value of check : {}, {}", count, s.getType());
                if (orPageResult.getDtoList().isEmpty()) {
                    continue;
                } else {
                    OpenRecipe testVal = orPageResult.getDtoList().get(0);
                    if (testVal != null) {
                        if (s.getType().equals(SearchType.RECIPE_NAME.getValue())) {
                            if (orPageResult.getDtoList().get(0).getRcpNm().contains(s.getKeyword())) count++;
                        }
                        if (s.getType().equals(SearchType.RECIPE_DETAILS.getValue())) {
                            if (orPageResult.getDtoList().get(0).getRcpPartsDtls().contains(s.getKeyword())) count++;
                        }
                        if (s.getType().equals(SearchType.RECIPE_PARTS.getValue())) {
                            if (orPageResult.getDtoList().get(0).getRcpPat2().contains(s.getKeyword())) count++;
                        }
                        if (s.getType().equals(SearchType.RECIPE_SEQUENCE.getValue())) {
                            if (orPageResult.getDtoList().get(0).getRcpSeq().toString().equals(s.getKeyword())) count++;
                        }
                        if (s.getType().equals(SearchType.RECIPE_WAY.getValue())) {
                            if (orPageResult.getDtoList().get(0).getRcpWay2().contains(s.getKeyword())) count++;
                        }
                    }
                }
            }

            if (orPageResult.getDtoList().isEmpty()) {
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
        int total = 1061;

        for (List<Search> valueTest : lists) {
            int i = 0;

            while (i < total) {
                PageRequest of = PageRequest.of(i, 100);

                // when
                Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchAndPageHandling(valueTest, of);
                Function<OpenRecipeEntity, OpenRecipe> fn = (OpenRecipeConverter::entityToDto);
                APIPageResult<OpenRecipe, OpenRecipeEntity> andPageResult = new APIPageResult<>(openRecipeEntities, fn);

                total = openRecipeEntities.getTotalPages();

                if (i > total) {
                    break;
                }

                // then
//                log.info("search value check of call : {}", orPageResult);

                for (Search s : valueTest) {

                    if (andPageResult.getDtoList().isEmpty()) {
                        continue;
                    } else {
                        OpenRecipe testVal = andPageResult.getDtoList().get(0);
                        if (testVal != null) {
                            if (s.getType().equals(SearchType.RECIPE_NAME.getValue())) {
                                assertThat(s.getKeyword()).isIn(andPageResult.getDtoList().get(0).getRcpNm());
                            }
                            if (s.getType().equals(SearchType.RECIPE_DETAILS.getValue())) {
                                assertThat(s.getKeyword()).isIn(andPageResult.getDtoList().get(0).getRcpPartsDtls());
                            }
                            if (s.getType().equals(SearchType.RECIPE_PARTS.getValue())) {
                                assertThat(s.getKeyword()).isEqualTo(andPageResult.getDtoList().get(0).getRcpPat2());
                            }
                            if (s.getType().equals(SearchType.RECIPE_SEQUENCE.getValue())) {
                                assertThat(Long.parseLong(s.getKeyword())).isEqualTo(andPageResult.getDtoList().get(0).getRcpSeq());
                            }
                            if (s.getType().equals(SearchType.RECIPE_WAY.getValue())) {
                                assertThat(s.getKeyword()).isEqualTo(andPageResult.getDtoList().get(0).getRcpWay2());
                            }
                        }
                    }
                }

                i++;
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

    @AfterEach
    void deleteAllInsertValue() {
        openRecipeRepository.deleteAll();
    }
}