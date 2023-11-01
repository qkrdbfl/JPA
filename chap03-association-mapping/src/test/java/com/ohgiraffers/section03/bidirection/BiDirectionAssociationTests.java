package com.ohgiraffers.section03.bidirection;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiDirectionAssociationTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    @Test
    public void 양방향_연관관계_매핑_조회_테스트() {
        //given
        int menuCode = 10;
        int categoryCode = 10;
        //WHEN
        //진짜 연관관계는 최초 조회 시 조인 결과를 인출한다.
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        //가짜 연관 관계는 Category 엔터티만 조회하고 필요 시 연관 관계 엔터티를 조회하는 쿼리를 다시 실행하게 된다.
        Category foundCategory = entityManager.find(Category.class, categoryCode);
        //then
        assertEquals(menuCode, foundMenu.getMenuCode());
        assertEquals(categoryCode, foundCategory.getCategoryCode());
        //find가 실행될떄 셀렉트 구문 어떻게 동작하는지 확인하기(카테고리 코드 조회) -> 카테고리 코드는 따로 조회되지 않았다.

        //주의사항 : toString 오버라이딩 시 양방향 연관 관계는 재귀 호출이 일어나기 때문에
        // stackOverflowError가 발생한다
        //엔터티의 주인이 아닌 쪽의 toString에서 연관 객체부분이 출력되지 않도록 삭제한다.
        //특히 자동완성 or 롬복 라이브러리 사용 시 주의 한다.
        System.out.println(foundMenu);// 둘다 출력하려고 하니 java.lang.StackOverflowError 에러가 남
        System.out.println(foundCategory); // 주인이 아닌 쪽(category)에서 참조 하는 쪽의 toString을 지우면 정상 수행됨

        //메뉴 리스트가 필요한 순간 다시 조회 쿼리가 동작하며 참조된다.
        foundCategory.getMenuList().forEach(System.out::println);
        //양방향 참조시 메뉴 -> 카테고리 -> 메뉴리스트 참조도 가능하다
        foundMenu.getCategory().getMenuList().forEach(System.out::println);

        //1. 가짜연관관계랑 진짜연관관계는 서로 참조 시 써야할 어노테이션이 다르다.
        //2. 가짜 연관관계에는 @OneToMany(mappedBy =) 에 참조 필드의 필드명 작성하기.
        //3. 양방향 참조 시 서로 부딪혀서 에러가 나니까 toString부분(필드 선언)에서 가짜 연관관계인 참조부분만 지운다.
    }

    @Test
    public void 양방향_연관관계_주인_객체를_이용한_삽입_테스트(){
        //given
        Menu menu= new Menu();
        menu.setMenuCode(125);
        menu.setMenuName("연관관계주인메뉴");
        menu.setMenuPrice(10000);
        menu.setOrderableStatus("Y");
        menu.setCategory(entityManager.find(Category.class, 4));//4번 카테고리에 해당하는걸 찾아온다
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(menu);
        entityTransaction.commit();
        //then
        Menu foundMenu = entityManager.find(Menu.class, menu.getMenuCode());// 인설트된 메뉴를 찾아와라
        assertEquals(menu.getMenuCode(), foundMenu.getMenuCode());//찾을 메뉴가 일치하는지 확인
        System.out.println(foundMenu);// 4번 카테고리에 잘 저장됐는지 확인
    }

    @Test
    public void 양방향_연관관계_주인이_아닌_객체를_이용한_삽입_테스트() {
        //given
        Category category = new Category();
        category.setCategoryCode(1004);
        category.setCategoryName("양방향카테고리");
        category.setRefCategoryCode(null);
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(category);
        entityTransaction.commit();
        //then
        Category foundCategory = entityManager.find(Category.class, category.getCategoryCode());
        assertEquals(category.getCategoryCode(), foundCategory.getCategoryCode());
        System.out.println(foundCategory);
    }
}
