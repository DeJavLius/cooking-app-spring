package teamproject.capstone.recipe.service.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import teamproject.capstone.recipe.service.recipe.OpenRecipePageWithSearchService;
import teamproject.capstone.recipe.service.recipe.OpenRecipeService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class OpenRecipeServiceImplTest {
    @Autowired
    OpenAPIHandler openAPIHandler;
    @Autowired
    OpenRecipeRepository openRecipeRepository;

    @Autowired
    OpenRecipeService openRecipeService;
    @Autowired
    OpenRecipePageWithSearchService openRecipePageWithSearchService;

    HashMap<String, Object> insertValue = new HashMap<>();

    private void mapInsertIn() {
        insertValue.put("name", "새우");
        insertValue.put("detail", "배추");
        insertValue.put("part", "반찬");
        insertValue.put("way", "찌기");
        insertValue.put("seq", 28L);
    }

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
        PageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openRecipePageWithSearchService.allAPIDataSources(pr);

        // then
        assertThat(openRecipeAPIPageResult.getDtoList().size()).isEqualTo(10);
        assertThat(openRecipeAPIPageResult.getNowPage()).isEqualTo(1);
    }

    @Test
    void openAPISearchAndOneSearch() {
        // given
        Search way = Search.builder().way("찌기").build();
        PageRequest of = PageRequest.of(0, 10);

        // when
        PageResult<OpenRecipe, OpenRecipeEntity> api = openRecipePageWithSearchService.searchAndAPIDataSources(way, of);

        // then
        assertThat(api.getDtoList().get(0).getRcpWay2()).isEqualTo(way.getWay());
    }

    @Test
    void openAPISearchOrHandling() {
        // given
        List<Search> lists = fiveCase();

        for (Search search : lists) {
            log.info("search value of : name - {}, detail - {}, way - {}, seq - {}, part - {}", search.getName(), search.getDetail(), search.getWay(), search.getSeq(), search.getPart());
            PageRequest of = PageRequest.of(0, 100);

            // when
            PageResult<OpenRecipe, OpenRecipeEntity> orAPIPageResult = openRecipePageWithSearchService.searchOrAPIDataSources(search, of);

            // then
            int count = 0;
            if (orAPIPageResult.getDtoList().isEmpty()) {
                continue;
            } else {
                OpenRecipe testVal = orAPIPageResult.getDtoList().get(0);
                if (testVal != null) {
                    if (search.getName() != null) {
                        if (orAPIPageResult.getDtoList().get(0).getRcpNm().contains(search.getName())) count++;
                    }
                    if (search.getDetail() != null) {
                        if (orAPIPageResult.getDtoList().get(0).getRcpPartsDtls().contains(search.getDetail())) count++;
                    }
                    if (search.getPart() != null) {
                        if (orAPIPageResult.getDtoList().get(0).getRcpPat2().contains(search.getPart())) count++;
                    }
                    if (search.getSeq() > 0L) {
                        if (orAPIPageResult.getDtoList().get(0).getRcpSeq().equals(search.getSeq())) count++;
                    }
                    if (search.getWay() != null) {
                        if (orAPIPageResult.getDtoList().get(0).getRcpWay2().contains(search.getWay())) count++;
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
        List<Search> lists = fiveCase();

        for (Search search : lists) {
            log.info("search value of : name - {}, detail - {}, way - {}, seq - {}, part - {}", search.getName(), search.getDetail(), search.getWay(), search.getSeq(), search.getPart());
            PageRequest of = PageRequest.of(0, 100);

            // when
            PageResult<OpenRecipe, OpenRecipeEntity> andAPIPageResult = openRecipePageWithSearchService.searchAndAPIDataSources(search, of);
            if (!andAPIPageResult.getDtoList().isEmpty()) {
                OpenRecipe testVal = andAPIPageResult.getDtoList().get(0);

                // then
                if (testVal != null) {
                    if (!search.getName().equals("")) {
                        assertThat(andAPIPageResult.getDtoList().get(0).getRcpNm()).contains((String) insertValue.getOrDefault("name", null));
                    }
                    if (!search.getDetail().equals("")) {
                        assertThat(andAPIPageResult.getDtoList().get(0).getRcpPartsDtls()).contains((String) insertValue.getOrDefault("detail", null));
                    }
                    if (!search.getPart().equals("")) {
                        assertThat(andAPIPageResult.getDtoList().get(0).getRcpPat2()).isEqualTo((String) insertValue.getOrDefault("part", null));
                    }
                    if (search.getSeq() > 0L) {
                        assertThat(insertValue.getOrDefault((Object) "seq", 0L)).isEqualTo(andAPIPageResult.getDtoList().get(0).getRcpSeq());
                    }
                    if (!search.getWay().equals("")) {
                        assertThat(andAPIPageResult.getDtoList().get(0).getRcpWay2()).isEqualTo((String) insertValue.getOrDefault("way", null));
                    }
                }
            }
        }
    }

    private List<Search> fiveCase() {
        List<Search> result = new ArrayList<>();
        List<List<String>> values = new ArrayList<>();
        List<String> testSearch = new ArrayList<>();

        mapInsertIn();

        testSearch.add("name");
        testSearch.add("detail");
        testSearch.add("part");
        testSearch.add("way");
        testSearch.add("seq");

        for (int i = 1; i < 6; i++) {
            List<List<String>> collect = Generator.combination(testSearch)
                    .simple(i)
                    .stream()
                    .collect(Collectors.toList());

            values.addAll(collect);
        }

        for (List<String> vals : values) {
            Search search = new Search();

            for (String v : vals) {
                if (v.equals("name")) {
                    search.setName((String) insertValue.getOrDefault("name", null));
                }
                if (v.equals("detail")) {
                    search.setDetail((String) insertValue.getOrDefault("detail", null));
                }
                if (v.equals("part")) {
                    search.setPart((String) insertValue.getOrDefault((Object) "part", null));
                }
                if (v.equals("way")) {
                    search.setWay((String) insertValue.getOrDefault((Object) "way", null));
                }
                if (v.equals("seq")) {
                    search.setSeq((Long) insertValue.getOrDefault((Object) "seq", 0L));
                }
            }

            result.add(search);
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