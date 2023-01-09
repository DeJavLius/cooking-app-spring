# cooking-app-spring
#
## Eco Recipe 사이트

- 제목 : Eco Recipe 웹 프로젝트

- 프로젝트 : 팀 프로젝트
  - 프로젝트 종류 : 안드로이드 앱, iOS, 웹
  - 팀 : [대학 캡스톤 팀 1조](https://github.com/college-capstone-team-1)
  - 깃헙 주소 : [cooking web project](https://github.com/college-capstone-team-1/cooking-app-spring)
  - 배포된 사이트 : [Recipe](http://www.inndiary.xyz)

- 개발 언어 : Java

- 프레임워크 : Spring, Spring Boot

- 개발 의존 : Spring Security, Spring MVC, Thymeleaf, JPA, QueryDSL, OAuth2.0

- 개발 환경 : intellij IDEA, HeidiSQL, AWS EC2, GCP(Google Cloud Platform)

- DB : MariaDB

- 기간 : 2022. 10. ~ 12.

- 배포 환경 : Amazone EC2, Amazone Route 53

- 로직 설계 : Notion, Figma

## 사용 API
     http://www.foodsafetykorea.go.kr/api/openApiInfo.do?menu_grp=MENU_GRP31&menu_no=661&show_cnt=10&start_idx=1&svc_no=COOKRCP01

---

# 요구사항
- API 서비스 제공
  - 안드로이드, iOS를 위한 Json API 제공
    - 모든 레시피를 페이징 처리(page)해서 보내는 양(size)을 지정하고 정렬 순서(order)를 정하고 요청할 수 있는 API
      - 순서 : 좋아요 많은 순, 일반순
    - 위와 같은 API에 And 연산 Query 검색이 가능한 API
      - 검색 가능 종류 :
        - 이름 (name), 조리방식 (way), 요리분류 (part), 재료 (detail), 레시피 순번 (sequence)
    - 좋아요 조회, 추가와 삭제 API
  - 웹 서비스용 API 제공
    - 좋아요를 동적으로 제공하기 위한 API

- 웹 서비스
  - 메인화면에서 사용자들이 좋아한 레시피 top 8가지를 보여줌
  - 제일 많은 좋아요를 받은 레시피와 관련된 카테고리의 레시피를 무작위로 추천
  - 모든 레시피를 조회 가능
    - 카테고리를 통해 레시피를 원하는 조건에 맞게 조회 가능
      - 이름, 조리방식, 요리분류, 재료
        - 이름, 재료는 텍스트 검색
        - 조리방식, 요리분류는 선택 검색
  - 레시피에 대해 상세하게 볼 수 있음
    - 원하는 레시피에 좋아요를 누를 수 있음
    - 상세히 누른 레시피 하단에 관련 카테고리 레시피 무작위로 추천
  - 로그인 사용자는 좋아요한 레시피와 사용자 정보를 조회할 수 있음
    - 앱, iOS 유저는 동일한 구글 로그인이 가능하다면 연동해 조회 가능

---

### 도메인
![](recipe/img/레시피_도메인_결과물.png)

### 설계

#### 초안
![](recipe/img/레시피_로직_초안.png)

#### 개발 완료 후
![](recipe/img/레시피_로직_결과물.png)

# 로직

### OpenAPI
![](recipe/img/레시피_로직_OpenAPI.png)

### 로그인
![](recipe/img/레시피_로직_로그인.png)

---

# 구현

## 메인화면
![](recipe/img/레시피_메인상단.png)
<div style="text-align: center;">메인화면 상단</div>

![](recipe/img/레시피_메인_추천레시피.png)
<div style="text-align: center;">메인화면 상단 추천 레시피</div>

![](recipe/img/레시피_메인_관련레시피.png)
<div style="text-align: center;">메인화면 하단 관련 레시피</div>

## 레시피 목록
![](recipe/img/레시피_보기상단.png)
<div style="text-align: center;">레시피 보기 상단</div>

![](recipe/img/레시피_보기_카테고리.png)
<div style="text-align: center;">레시피 보기 카테고리</div>

![](recipe/img/레시피_보기_모든레시피.png)
<div style="text-align: center;">레시피 보기 모든 목록</div>

## 레시피 상세정보
![](recipe/img/레시피_상세보기_비로그인.png)
<div style="text-align: center;">레시피 상세보기</div>

![](recipe/img/레시피_상세보기_조리과정.png)
<div style="text-align: center;">레시피 상세보기 조리과정</div>

![](recipe/img/레시피_상세보기_관련_카테고리추천.png)
<div style="text-align: center;">레시피 상세와 관련있는 레시피 추천</div>

## 로그인
![](recipe/img/레시피_로그인.png)
<div style="text-align: center;">레시피 팝업 구글 로그인 지원</div>

---

# 코드
## API
### 앱 / 어플 통신용 API 구현
- 웹에서 API를 구현해 앱과 iOS에 레시피 데이터를 JSON을 통해 전달
- 공통 쿼리 : page, size, order (d : 일반순, r : 인기순)
- 모든 레시피 조회 : `/api/v1`
  - 검색 조건 쿼리 : name (String), detail (String), part (String), way (String), seq (Long)
  - and 검색 레시피 조회 : `/api/v1/search/find-only`
  - or 검색 레시피 조회 : `/api/v1/search/find-with`
- 좋아요 많은 순서로 8개 레시피 조회 : `/api/v1/recipes/rank`
- 테스트용 임시 API
  - 오픈 DB로부터 모든 데이터 저장 : `/api/v2/save`
  - 현재 DB의 모든 레시피 데이터 삭제 : `/api/v2/delete/all`

```java
@RestController
public class OpenAPIController {
    
    /*
            모든 레시피를 조회하는 API로 page, size, order 3가지 parameter 지원
            기본값 : 0, "d"로 parameter 값을 주지 않아도 사용 가능
     */
    @GetMapping(value = "/v1", produces = "application/json; charset=UTF-8")
    public RecipeData responseOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_ORDER) String order) {

        // 정렬 d 일 경우 기본 순, f 일 경우 favorite - 좋아요 많은 순
        Sort sort = order.equals("f") ? Sort.by("favorite").descending() : Sort.by("id").ascending();
        PageRequest pageRequest = searchWithPageHandler.choosePage(page, size, sort);

        // 페이지 설정을 보내 해당 설정에 맞게 모든 레시피 조회
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openRecipePageWithSearchService.allAPIDataSources(pageRequest);
      
        // 페이지의 끝인지, 최종 페이지는 몇 페이지인지, 총 몇 개가 찾아졌는지
        boolean isEnd = page == TotalValue.getTotalCount();
        Meta metaInfo = MetaDelegator.metaGenerator(isEnd, openRecipeAPIPageResult.getTotalPage(), TotalValue.getTotalCount());
      
        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipeAPIPageResult.getDtoList())
                .build();
    }

    /*
            레시피를 검색 조건에 따라 조회하는 API로 page, size, order에 Search 객체를 parameter로 지원
            Search는 id, 이름, 조리 방법, 요리 구분, 식재료를 조건으로 설정, and 검색
     */
    @GetMapping(value = "/v1/search/find-only", produces = "application/json; charset=UTF-8")
    public RecipeData responseSearchAndOpenAPI(@RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_ORDER) String order, Search value) {
        Sort sort = order.equals("f") ? Sort.by("favorite").descending() : Sort.by("id").ascending();
        SearchWithPageRequest searchWithPageRequest = searchWithPageHandler.choosePageWithSearch(value, page, size);
        
        // 페이지 설정과 검색 조건을 담은 객체를 전달
        APIPageResult<OpenRecipe, OpenRecipeEntity> openRecipeAPIPageResult = openRecipePageWithSearchService.searchAndAPIDataSources(searchWithPageRequest.getSearch(), searchWithPageRequest.detailOfSort(sort));
      
        boolean isEnd = page == TotalValue.getTotalCount();
        Meta metaInfo = MetaDelegator.metaGenerator(isEnd, openRecipeAPIPageResult.getTotalPage(), TotalValue.getTotalCount());
      
        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipeAPIPageResult.getDtoList())
                .build();
    }

    /*
            좋아요 레시피를 많은 순으로 상위 8가지를 조회하는 API
     */
    @GetMapping(value = "/v1/recipes/rank", produces = "application/json; charset=UTF-8")
    public RecipeData responseFavoriteOpenAPI() {
        List<OpenRecipe> openRecipes = openRecipePageWithSearchService.mostAndroidRecipe();
    
        Meta metaInfo = MetaDelegator.metaGenerator(true, openRecipes.size(), 0);
    
        return RecipeData.builder()
                .meta(metaInfo)
                .openRecipes(openRecipes)
                .build();
    }
  
    /*
            임시 API로 공공 데이터의 레시피 데이터를 가져와 저장
    */
    @GetMapping("/v2/save")
    public String saveOpenAPI() {
        
        // 공공 데이터를 요청
        List<OpenAPIRecipe> openAPIRecipes = openApiHandler.requestAllOpenAPI();
        List<OpenRecipe> totalRecipes = new ArrayList<>();
    
        for (OpenAPIRecipe cr : openAPIRecipes) {
            
            // 저장할 수 있도록 받아진 Json 데이터를 entity로 변환 
            totalRecipes.addAll(cr.getRow().stream().map(OpenAPIDelegator::rowToOpenRecipe).collect(Collectors.toList()));
        }
        
        openRecipeService.createAll(totalRecipes);
    
        return "데이터 저장 완료 원래 화면으로 돌아가세요. 테스트용 임시 url";
    }
}
```

```java
// 레시피 조회 관련 service
public interface OpenRecipePageWithSearchService {
    // 모든 레시피 조회
    APIPageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest);
  
    // 조건 검색 레시피 조회
    APIPageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(Search search, PageRequest pageRequest);
}

@Service
public class RecipeAndSearchServiceImpl implements OpenRecipePageWithSearchService {
    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> allAPIDataSources(PageRequest pageRequest) {
        // API page 생성 시 Stream 함수의 map에 function 자리에 들어감
        // Functional interface로 entity를 DTO로 바꿔 Json으로 전달하도록 함
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        
        // DB로 조회된 값
        Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPIPageHandling(pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }

    @Override
    public APIPageResult<OpenRecipe, OpenRecipeEntity> searchAndAPIDataSources(Search search, PageRequest pageRequest) {
        Function<OpenRecipeEntity, OpenRecipe> function = (OpenRecipeConverter::entityToDto);
        
        // 검색이 추가됨
        Page<OpenRecipeEntity> openRecipeEntities = openRecipePageWithSearchRepository.openAPISearchAndPageHandling(search, pageRequest);
        return new APIPageResult<>(openRecipeEntities, function);
    }
}

// API page 설정
public class APIPageResult<DTO, EN> extends PageResult<DTO, EN> {
    /*
            API의 page 처리를 위한 객체
            
            추상 클래스 PageResult를 구현
     */
  
    public APIPageResult(Page<EN> result, Function<EN, DTO> fn) {
        super(result, fn);
    }
  
    @Override
    public void makePageList(Pageable pageable) {
        setPage(pageable);
        boolean isLast = super.getNowPage() == super.getTotalPage();
        super.setLastPage(isLast);
    }
  
    @Override
    public void setPage(Pageable pageable) {
        super.setNowPage(pageable.getPageNumber() + 1);
    }
}

// 레시피 DB 조회 repository
public interface OpenRecipePageWithSearchRepository {
    Page<OpenRecipeEntity> openAPIPageHandling(Pageable pageable);
  
    Page<OpenRecipeEntity> openAPISearchAndPageHandling(Search searchKeywords, Pageable pageable);
}

@Repository
public class RecipeTupleAndPageWithSearchRepositoryImpl extends QuerydslRepositorySupport implements OpenRecipePageWithSearchRepository {
    // 기존의 EntityManager에 필요한 EntityFactory를 생성해 주입
    @PersistenceContext
    private EntityManager entityManager;
    
    // 쿼리 DSL 생성 entity
    private final QOpenRecipeEntity openRecipeEntity = QOpenRecipeEntity.openRecipeEntity;

    public RecipeTupleAndPageWithSearchRepositoryImpl() {
        // querydsl 설정
        super(OpenRecipeEntity.class);
    }

    // 모든 레시피 조회
    @Override
    public Page<OpenRecipeEntity> openAPIPageHandling(Pageable pageable) {
        
        // 정렬 시 레시피의 좋아요 개수는 정렬 기준인 OpenRecipeEntity에 존재하지 않기 때문에 NumberPath를 통해 개별로 지정을 해줘야 함
        NumberPath<Long> aliasRecipe = Expressions.numberPath(Long.class, "id");
        
        // count가 포함되어 있기 때문에 Tuple로 조회됨
        JPAQuery<Tuple> openAPIDataHandle = withSelectInit(aliasRecipe);
        
        // 최종적으로 Page 객체 반환
        return pagingWithSortHandler(openAPIDataHandle, aliasRecipe, pageable);
    }
  
    @Override
    public Page<OpenRecipeEntity> openAPISearchAndPageHandling(Search searchKeywords, Pageable pageable) {
        NumberPath<Long> aliasRecipe = Expressions.numberPath(Long.class, "id");
        JPAQuery<Tuple> openAPIDataHandle = withSelectInit(aliasRecipe);
        
        // And 검색을 위한 where 절 조회
        openAPIDataHandle.where(searchAndQueryBuilder(searchKeywords));
        return pagingWithSortHandler(openAPIDataHandle, aliasRecipe, pageable);
    }

    // left join을 통해 Favorite Recipe DB의 Recipe의 개수를 count
    private JPAQuery<Tuple> withSelectInit(NumberPath<Long> aliasRecipe) {
        return jpaQueryWithCountStart(aliasRecipe).leftJoin(favoriteEntity).on(favoriteEntity.recipe.id.eq(openRecipeEntity.id)).groupBy(openRecipeEntity.id);
    }
  
    // select 문에서 OpenRecipe와 count를 조회
    private JPAQuery<Tuple> jpaQueryWithCountStart(NumberPath<Long> aliasRecipe) {
        return jpaQueryOpenInit().from(openRecipeEntity).select(openRecipeEntity, favoriteEntity.recipe.id.count().as(aliasRecipe));
    }

    // entityManager를 통해 JPA Query 생성
    private JPAQuery<OpenRecipeEntity> jpaQueryOpenInit() {
        return new JPAQuery<>(entityManager);
    }
    
    private Page<OpenRecipeEntity> pagingWithSortHandler(JPAQuery<Tuple> query, NumberPath<Long> aliasRecipe, Pageable pageable) {
        totalCountSetting(query.fetch().size());
        
        // JPAQuery page의 sort를 통해 정렬함 
        pageSortSetting(query, aliasRecipe, pageable.getSort());
        
        List<Tuple> tupleResult = sqlTuplePageSetting(query, pageable);

        // tuple로 조회된 데이터를 object[]로 전환해 0번째 인덱스의 값을 가져옴 (OpenRecipe 값) - 1번 인덱스는 count 값 그리고 stream을 통해 list를 생성
        List<OpenRecipeEntity> result = tupleResult.stream().map(tuple -> (OpenRecipeEntity) tuple.toArray()[0]).collect(Collectors.toList());
        long count = TotalValue.getTotalCount();
        return new PageImpl<>(result, pageable, count);
    }

    private void pageSortSetting(JPQLQuery<Tuple> query, NumberPath<Long> aliasRecipe, Sort pageSort) {
        
        // page sort의 구체적인 방식으로 querydsl에서 지원하는 정렬보다 상세하게 설정하기 위해 사용
        pageSort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(OpenRecipeEntity.class, "openRecipeEntity");
            
            // 만약 prop이 id, 즉 기존의 정렬이라면 위처럼 openRecipeEntity에 있는 값이므로 PathBuilder를 통해 orderExpression을 생성해도 되지만
            if (prop.equals("favorite")) {
                
                // 좋아요의 개수는 없으므로 numberPath를 통해 정렬
                query.orderBy(aliasRecipe.desc());
            } else {
                query.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
            }
        });
    }

    private List<Tuple> sqlTuplePageSetting(JPAQuery<Tuple> openAPIDataHandle, Pageable pageable) {
        log.info("page offset value : {} / page size value : {}", pageable.getOffset(), pageable.getPageSize());
        
        // offset 번째 page, size 설정 
        openAPIDataHandle.offset(pageable.getOffset()).limit(pageable.getPageSize());
        return openAPIDataHandle.fetch();
    }
    
    private BooleanBuilder searchAndQueryBuilder(Search keywords) {
        BooleanBuilder queryResult = defaultBooleanBuilder();
        
        // and 조건문 설정
        queryResult.and(detailQuery(keywords.getDetail())
                .and(nameQuery(keywords.getName()))
                .and(partQuery(keywords.getPart()))
                .and(wayQuery(keywords.getWay()))
        );
    
        // seq는 0L일 경우 조회가 안되기 때문에 0L 이상일 경우만 검색
        if (keywords.getSeq() > 0L) {
            queryResult.and(seqQuery(keywords.getSeq()));
        }
        return queryResult;
    }

    private BooleanBuilder defaultBooleanBuilder() {
        BooleanExpression booleanExpression = openRecipeEntity.id.gt(0L);
        return new BooleanBuilder().and(booleanExpression);
    }
  
    private BooleanExpression nameQuery(String name) {
        return openRecipeEntity.rcpNm.contains(name);
    }
  
    private BooleanExpression detailQuery(String detail) {
        return openRecipeEntity.rcpPartsDtls.contains(detail);
    }
  
    private BooleanExpression partQuery(String part) {
        return openRecipeEntity.rcpPat2.contains(part);
    }
  
    private BooleanExpression wayQuery(String way) {
        return openRecipeEntity.rcpWay2.contains(way);
    }
  
    private BooleanExpression seqQuery(Long seq) {
        return openRecipeEntity.rcpSeq.eq(seq);
    }
}
```

#### 조회 API 
모든 레시피를 조회하는 API의 경우 페이지, 정렬 관련 Query만 필요합니다. 검색 조회 API는 Search 객체를 만들어 Query로 받도록 했습니다.

#### 인기 레시피 API
좋아요 레시피를 가져오는 API는 가장 좋아요가 많은 순서로 8가지 레시피를 전송합니다.

#### Open API 저장 테스트용 API
테스트용 API로 공공 Open Recipe API의 모든 데이터를 서버 DB로 저장합니다.

#
- 좋아요 관련 API
  - 좋아요 객체를 JSON으로 전달
  - 쿼리 : recipe id (레시피 식별 번호) (Long), user uid (firebase 유저 uid) (String)
  - give (GET 조회)
    - 모든 좋아요 조회 : `/api/v1/favorites/give`
    - 사용자의 좋아요 조회 : `/api/v1/favorites/give/user`
      - 쿼리 : uid
    - 레시피의 좋아요 조회 : `/api/v1/favorites/give/recipe`
      - 쿼리 : recipe sequence
  - take (POST 좋아요 추가)
    - 유저의 모든 좋아요 리스트 저장 : `/api/v1/favorites/take`
      - 쿼리 : uid
      - request body : recipe sequence list 
    - 유저의 특정 좋아요 저장 : `/api/v1/favorites/take/choose`
      - 쿼리 : uid, recipe sequence
  - delete (POST 좋아요 삭제)
    - 모든 좋아요 삭제 : `/api/v1/favorites/delete`
    - 해당 유저의 모든 좋아요 삭제 : `/api/v1/favorites/delete/user`
      - 쿼리 : uid
    - 유저의 좋아요 삭제 : `/api/v1/favorites/delete/choose`
      - 쿼리 : uid, recipe sequence

```java
@RestController
public class RecipeAPIController {
    @GetMapping("/give/user")
    public FavoriteData requestUsersFavoriteRecipe(@RequestParam String uid) {
        List<Favorite> findValues = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            findValues = favoriteService.findByEmail(email);
        }

        List<FavoriteRecipe> favoriteRecipes = favoriteRecipeSerialization(findValues);

        return FavoriteData.builder()
                .count(findValues.size())
                .favoriteRecipes(favoriteRecipes)
                .build();
    }

    @PostMapping("/take/choose")
    public FavoriteData responseOneFavoriteRecipe(@RequestParam String uid, @RequestParam Long recipeSeq) {
        List<Favorite> savedValues = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            OpenRecipe recipe = openRecipeService.findByRecipeSeq(recipeSeq);
            Favorite favorite = favoriteService.findRecipe(recipeSeq, email);

            if (favorite.getId().equals(0L)) {
                Favorite build = Favorite.builder()
                        .recipeId(recipe.getId())
                        .recipeSeq(recipe.getRcpSeq())
                        .userEmail(email)
                        .build();

                favorite = favoriteService.create(build);
            }
            savedValues.add(favorite);
        }

        return FavoriteData.builder()
                .count(savedValues.size())
                .favoriteRecipes(favoriteRecipeSerialization(savedValues))
                .build();
    }

    @PostMapping("/delete/choose")
    public FavoriteData deleteFavoriteRecipe(@RequestParam String uid, @RequestParam Long recipeSeq) {
        List<Favorite> deleteCheck = new ArrayList<>();

        if (firebaseUserManager.isAppUserByUid(uid)) {
            String email = firebaseUserManager.findEmailByUid(uid);
            Favorite recipe = favoriteService.findRecipe(recipeSeq, email);
            favoriteService.delete(recipe);

            deleteCheck = favoriteService.findByEmail(email);
        }

        return FavoriteData.builder()
                .count(deleteCheck.size())
                .favoriteRecipes(favoriteRecipeSerialization(deleteCheck))
                .build();
    }
    
    private List<FavoriteRecipe> favoriteRecipeSerialization(List<Favorite> values) {
        return values.stream().map(this::serialization).collect(Collectors.toList());
    }

    private FavoriteRecipe serialization(Favorite favorite) {
        return FavoriteRecipe.builder()
                .id(favorite.getId())
                .recipeId(favorite.getRecipeId())
                .recipeSeq(favorite.getRecipeSeq())
                .userEmail(favorite.getUserEmail())
                .build();
    }
}
```

앱 / iOS 활용 좋아요 API

give (Get) API를 통해 





#
## 메인
- 가장 첫 화면에서는 

