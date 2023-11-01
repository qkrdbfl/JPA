package com.ohgiraffers.section06.join;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JoinTests {

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
    public void 내부조인을_이용한_조회_테스트() {
        /* Menu 엔티티에 대한 조회만 일어나고 Category 엔티티에 대한 조회는 나중에 필요할때 일어난다.
         * select의 대상은 영속화하여 가져오지만 조인의 대상은 영속화하여 가져오지 않는다. */
        //when
        String jpql = "SELECT m FROM menu_section06 m JOIN m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void 외부조인을_이용한_조회_테스트() {
        //given
        String jpql = "SELECT m.menuName, c.categoryName FROM menu_section06 m RIGHT JOIN m.category c"//RIGHT JOIN m.category c : 카테고리 기준으로 조인한다는 의미
                + " ORDER BY m.category.categoryCode";
        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 컬렉션조인을_이용한_조회_테스트() {
        /* 컬렉션 조인은 의미상 분류 된 것으로 컬렉션을 지니고 있는 엔티티를 기준으로 조인하는 것을 말한다. */
        //when
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section06 c LEFT JOIN c.menuList m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 세타조인을_이용한_조회_테스트() {
        /* 세타 조인은 조인 되는 모든 경우의 수를 다 반환하는 크로스 조인과 같다. */
        //when
        String jpql = "SELECT c.categoryName, m.menuName FROM category_section06 c, menu_section06 m";
        List<Object[]> categoryList = entityManager.createQuery(jpql, Object[].class).getResultList();
        //then
        assertNotNull(categoryList);
        categoryList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.println(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 페치조인을_이용한_조회_테스트() {
        //when
        String jpql = "SELECT m FROM menu_section06 m JOIN FETCH m.category c"; //FETCH 추가
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}