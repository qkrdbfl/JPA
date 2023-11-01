package com.ohgiraffers.section03.projection;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectionTests {
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
    public void 단일_엔티티_프로젝션_테스트() { // (SELECT {프로젝션 대상} FROM)
        //when
        String jpql = "SELECT m FROM menu_section03 m";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        menuList.get(0).setMenuName("test1");
        entityTransaction.commit();
    }

    @Test
    public void 양방향_연관관계_엔터티_프로젝션_테스트() {
        //given
        int menuCodeParameter = 3;
        //when                              //bidirection_menu -> menuDTO쓸때 엔티티 이름과 동일하게 해주기 (@Entity(name = "bidirection_menu"))
        String jpql = "SELECT m.category FROM bidirection_menu m WHERE m.menuCode = :menuCode";
        BiDirectionCategory categoryOfMenu = entityManager.createQuery(jpql, BiDirectionCategory.class)
                .setParameter("menuCode", menuCodeParameter)
                .getSingleResult();
        //then
        System.out.println(categoryOfMenu);
        System.out.println(categoryOfMenu.getMenuList());

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        categoryOfMenu.setCategoryName("tset2");
        categoryOfMenu.getMenuList().get(1).setMenuName("test3");
        entityTransaction.commit();
    }

    @Test
    public void 임베디드_타입_프로젝션_테스트() {
        //when
        String jpql = "SELECT m.menuInfo FROM embedded_menu m";
        List<MenuInfo> menuInfoList = entityManager.createQuery(jpql, MenuInfo.class).getResultList();
        //then
        assertNotNull(menuInfoList);
        menuInfoList.forEach(System.out::println);
    }

    @Test
    public void TypedQuery를_이용한_스칼라_타입_프로젝션_테스트() {
        //when
        String jpql = "SELECT c.categoryName FROM category_section03 c"; //행자체는 여러개일테니 List<String>로 써준다
        List<String> categoryNameList = entityManager.createQuery(jpql, String.class).getResultList();
        //then
        assertNotNull(categoryNameList);
        categoryNameList.forEach(System.out::println);
    }

    @Test
    public void Query를_이용한_스칼라_타입_프로젝션_테스트() {
        //when
        String jpql = "SELECT c.categoryCode, c.categoryName FROM category_section03 c";
        List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(row -> { //배열안에 있는걸 카테고리코드와 카테고리이름 하나하나 보여주려면 람다식으로 row(행)을 스트림으로 작성해달라고 씀
            Arrays.stream(row).forEach(System.out::println);
        }); //이렇게해도 되지만 좀 번거로움. 밑의 구문에서 더 편하게 바꿔보자.
    }

    @Test
    public void  new_명령어를_활용한_프로젝션__테스트() {
        //when                          //경로로 매개변수 생성자를 호출
        String jpql = "SELECT new com.ohgiraffers.section03.projection.CategoryInfo(c.categoryCode, c.categoryName) FROM category_section03 c";
        List<CategoryInfo> categoryList = entityManager.createQuery(jpql).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(System.out::println);
    }
}
