package teamproject.capstone.recipe.repository.api;

import com.querydsl.core.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.paukov.combinatorics3.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import teamproject.capstone.recipe.domain.recipe.Favorite;
import teamproject.capstone.recipe.domain.recipe.Recommend;
import teamproject.capstone.recipe.entity.recipe.FavoriteEntity;
import teamproject.capstone.recipe.repository.recipe.*;
import teamproject.capstone.recipe.service.recipe.FavoriteRankService;
import teamproject.capstone.recipe.utils.api.APIPageResult;
import teamproject.capstone.recipe.utils.page.RecipePageResult;
import teamproject.capstone.recipe.utils.page.Search;
import teamproject.capstone.recipe.utils.api.json.parts.OpenAPIRecipe;
import teamproject.capstone.recipe.domain.recipe.OpenRecipe;
import teamproject.capstone.recipe.utils.api.json.parts.Row;
import teamproject.capstone.recipe.entity.recipe.OpenRecipeEntity;
import teamproject.capstone.recipe.utils.api.openApi.OpenAPIDelegator;
import teamproject.capstone.recipe.utils.api.openApi.OpenAPIHandler;
import teamproject.capstone.recipe.utils.converter.OpenRecipeConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    RecipeTupleRepository recipeTupleRepository;
    @Autowired
    FavoriteSimpleRepository favoriteSimpleRepository;

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
//        List<OpenAPIRecipe> fetchValues = openAPIHandler.requestAllOpenAPI();
//        List<OpenRecipeEntity> forInsert = new ArrayList<>();
//        for (OpenAPIRecipe oap: fetchValues) {
//            for (Row r : oap.getRow()) {
//                OpenRecipe op = OpenAPIDelegator.rowToOpenRecipe(r);
//                OpenRecipeEntity ope = OpenRecipeConverter.dtoToEntity(op);
//                forInsert.add(ope);
//            }
//            openRecipeRepository.saveAll(forInsert);
//            forInsert.clear();
//        }
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
        int total = 1058;
        List<Search> lists = fiveCase();

        for (Search search : lists) {
            log.info("search value of : name - {}, detail - {}, way - {}, seq - {}, part - {}", search.getName(), search.getDetail(), search.getWay(), search.getSeq(), search.getPart());
            PageRequest of = PageRequest.of(0, 100);

            // when
            Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchOrPageHandling(search, of);
            Function<OpenRecipeEntity, OpenRecipe> fn = (OpenRecipeConverter::entityToDto);
            APIPageResult<OpenRecipe, OpenRecipeEntity> orPageResult = new APIPageResult<>(openRecipeEntities, fn);

            total = openRecipeEntities.getTotalPages();

            // then
//                log.info("search value check of call : {}", orPageResult);

            int count = 0;
            if (orPageResult.getDtoList().isEmpty()) {
                continue;
            } else {
                OpenRecipe testVal = orPageResult.getDtoList().get(0);
                if (testVal != null) {
                    if (search.getName() != null) {
                        if (orPageResult.getDtoList().get(0).getRcpNm().contains(search.getName())) count++;
                    }
                    if (search.getDetail() != null) {
                        if (orPageResult.getDtoList().get(0).getRcpPartsDtls().contains(search.getDetail())) count++;
                    }
                    if (search.getPart() != null) {
                        if (orPageResult.getDtoList().get(0).getRcpPat2().contains(search.getPart())) count++;
                    }
                    if (search.getSeq() > 0L) {
                        if (orPageResult.getDtoList().get(0).getRcpSeq().equals(search.getSeq())) count++;
                    }
                    if (search.getWay() != null) {
                        if (orPageResult.getDtoList().get(0).getRcpWay2().contains(search.getWay())) count++;
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
        int total = 1058;
        List<Search> lists = fiveCase();

        for (Search search : lists) {
            int i = 0;

            while (i < total) {
                PageRequest of = PageRequest.of(i, 100);

                // when
                Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchAndPageHandling(search, of);
                Function<OpenRecipeEntity, OpenRecipe> fn = (OpenRecipeConverter::entityToDto);
                APIPageResult<OpenRecipe, OpenRecipeEntity> andPageResult = new APIPageResult<>(openRecipeEntities, fn);

                if (!andPageResult.getDtoList().isEmpty()) {
                    log.info("page request value check : {}", andPageResult.getDtoList());

                    total = openRecipeEntities.getTotalPages();

                    if (i > total) {
                        break;
                    }

                    // then
                    if (!search.getName().equals("")) {
                        assertThat(andPageResult.getDtoList().get(0).getRcpNm()).contains((String) insertValue.getOrDefault("name", null));
                    }
                    if (!search.getDetail().equals("")) {
                        assertThat(andPageResult.getDtoList().get(0).getRcpPartsDtls()).contains((String) insertValue.getOrDefault("detail", null));
                    }
                    if (!search.getPart().equals("")) {
                        assertThat(andPageResult.getDtoList().get(0).getRcpPat2()).isEqualTo((String) insertValue.getOrDefault("part", null));
                    }
                    if (search.getSeq() > 0L) {
                        assertThat(insertValue.getOrDefault((Object) "seq", 0L)).isEqualTo(andPageResult.getDtoList().get(0).getRcpSeq());
                    }
                    if (!search.getWay().equals("")) {
                        assertThat(andPageResult.getDtoList().get(0).getRcpWay2()).isEqualTo((String) insertValue.getOrDefault("way", null));
                    }
                }

                i++;
            }
        }
    }

    @Test
    void openAPISearchSimpleAndHandling() {
        // given
        int total = 1058;
        Search search = Search.builder().part("찌기").build();

        PageRequest of = PageRequest.of(0, 100);

        // when
        Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchAndPageHandling(search, of);
        Function<OpenRecipeEntity, OpenRecipe> fn = (OpenRecipeConverter::entityToDto);
        APIPageResult<OpenRecipe, OpenRecipeEntity> andPageResult = new APIPageResult<>(openRecipeEntities, fn);

        if (!andPageResult.getDtoList().isEmpty()) {
            log.info("page request value check : {}", andPageResult.getDtoList());

            // then
            if (!search.getName().equals("")) {
                assertThat(andPageResult.getDtoList().get(0).getRcpNm()).contains((String) insertValue.getOrDefault("name", null));
            }
            if (!search.getDetail().equals("")) {
                assertThat(andPageResult.getDtoList().get(0).getRcpPartsDtls()).contains((String) insertValue.getOrDefault("detail", null));
            }
            if (!search.getPart().equals("")) {
                assertThat(andPageResult.getDtoList().get(0).getRcpPat2()).isEqualTo((String) insertValue.getOrDefault("part", null));
            }
            if (search.getSeq() > 0L) {
                assertThat(insertValue.getOrDefault((Object) "seq", 0L)).isEqualTo(andPageResult.getDtoList().get(0).getRcpSeq());
            }
            if (!search.getWay().equals("")) {
                assertThat(andPageResult.getDtoList().get(0).getRcpWay2()).isEqualTo((String) insertValue.getOrDefault("way", null));
            }
        } else {
            log.info("nothing found");
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
    void randValueSelectTest() {
        // given
        String way = "찌기";
//        String part = "밥";
        Search test = Search.builder().way(way).build();
        PageRequest of = PageRequest.of(0, 500);
        List<Recommend> recommends = new ArrayList<>();

        List<Object[]> objects = recipeTupleRepository.sameRecommendRecipe(test);

        // then
        for (Object[] ol : objects) {
            Long id = (Long) ol[0];
            String image = (String) ol[1];
            String name = (String) ol[2];

            Recommend build = Recommend.builder().id(id).image(image).name(name).build();
            log.info("recommend value : id - {}, image - {}, name - {}", build.getId(), build.getImage(), build.getName());
            recommends.add(build);
        }

        for (Recommend r : recommends) {
            Optional<OpenRecipeEntity> byId = openRecipeRepository.findById(r.getId());
            byId.ifPresent(openRecipeEntity -> assertThat(openRecipeEntity.getId()).isEqualTo(r.getId()));
        }
    }

    @Test
    void tupleCountWithSearchPage() {
        // given
        Search test = Search.builder().build();
        PageRequest of = PageRequest.of(0, 1058);
        Page<Object[]> tuples = openRecipePageWithSearchRepository.recipeSearchAndPageSeparateHandling(test, of);

        List<FavoriteEntity> all = favoriteSimpleRepository.findAll();

        // when
        Function<Object[], Favorite> fn = (entity -> Favorite.builder().recipeId((Long) entity[0]).recipeMainImage((String) entity[1]).recipePart((String) entity[2]).recipeName((String) entity[3]).count((Long) entity[4]).build());
        RecipePageResult<Favorite, Object[]> value = new RecipePageResult<>(tuples, fn);

        List<Favorite> dtoList = value.getDtoList();
        int count = 0;
        for (Favorite f : dtoList) {
            log.info("value test of etc : {}, {}, {}, {}, {}", f.getRecipeId(), f.getRecipeMainImage(), f.getRecipePart(), f.getRecipeName(), f.getCount());
            for (FavoriteEntity fe : all) {
                if (f.getRecipeId().equals(fe.getId())) {
                    count++;
                }
            }
        }

        assertThat(count).isEqualTo(all.size());
    }

    @AfterEach
    void deleteAllInsertValue() {
//        favoriteSimpleRepository.deleteAll();
//        openRecipeRepository.deleteAll();
    }
}