package com.ohgiraffers.section01.simple;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NativeQueryTests {

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
    public void 결과_타입을_정의한_네이티브_쿼리_사용_테스트() {
        //given
        int menuCodeParameter = 15;
        //when
        /* DBMS의 고유한 오라클SQL문법을 작성한다. 위치 기반 파라미터로만 사용이 가능하다. */
        String query = "SELECT MENU_CODE, MENU_NAME, MENU_PRICE, CATEGORY_CODE, ORDERABLE_STATUS"
                + " FROM TBL_MENU WHERE MENU_CODE = ?";

        /* 일부 컬럼만 조회하는 것은 불가능하다. 열 부적합 에러가 남.*/
        //String query = "SELECT menu_code, menu_name, menu_price FROM tbl_menu WHERE menu_code = ?";

        Query nativeQuery = entityManager.createNativeQuery(query, Menu.class)
                .setParameter(1, menuCodeParameter);
        Menu foundMenu = (Menu) nativeQuery.getSingleResult(); //Menu //foundMenu
        //then
        assertNotNull(foundMenu);
        //영속성 컨텍스트에서 관리하는 객체임을 알 수 있다.
        assertTrue(entityManager.contains(foundMenu));
        assertTrue(entityManager.contains(foundMenu)); //위의 foundMenu가 영속성컨텍스트에서 관리되는지 확인하기 위한 코드
        System.out.println(foundMenu);
    }

    @Test
    public void 결과_타입을_정의할_수_없는_경우_조회_테스트() {
        //when
        String query = "SELECT MENU_NAME, MENU_PRICE FROM TBL_MENU";
        List<Object[]> menuList = entityManager.createNativeQuery(query).getResultList();
        /* 결과 타입을 특정하는 것 자체가 불가능하다. 엔티티로 매핑 시킬 경우에만 클래스 타입을 입력한다. */
        //List<Object[]> menuList = entityManager.createNativeQuery(query, Object[].class).getResultList();// 언노운 엔티티 오류남 -> Object[].class 엔티티가 아닐떄 저렇게 쓰면 오류남
        //then
        assertNotNull(menuList);
        menuList.forEach(row -> {
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 자동_결과_매핑을_사용한_조회_테스트() {
        //when
        String query = "SELECT "
                + " A.CATEGORY_CODE, A.CATEGORY_NAME, A.REF_CATEGORY_CODE, NVL(V.MENU_COUNT, 0) MENU_COUNT "
                + " FROM TBL_CATEGORY A "
                + " LEFT JOIN (SELECT COUNT(*) AS MENU_COUNT, B.CATEGORY_CODE "
                + " FROM TBL_MENU B "
                + " GROUP BY B.CATEGORY_CODE) V ON (A.CATEGORY_CODE = V.CATEGORY_CODE)"
                + " ORDER BY 1";

        Query nativeQuery = entityManager.createNativeQuery(query, "categoryCountAutoMapping");
        List<Object[]> categoryList = nativeQuery.getResultList();
        //then
        assertNotNull(categoryList);
        assertTrue(entityManager.contains(categoryList.get(0)[0])); //영속석컨텍스트에서 관리되는지 확인 코드
        categoryList.forEach(row -> { //출력 : Category{categoryCode=6, categoryName='일식', refCategoryCode=1} 4
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }

    @Test
    public void 수동_결과_매핑을_사용한_조회_테스트() {
        //when
        String query = "SELECT "
                + " A.CATEGORY_CODE, A.CATEGORY_NAME, A.REF_CATEGORY_CODE, NVL(V.MENU_COUNT, 0) MENU_COUNT "
                + " FROM TBL_CATEGORY A "
                + " LEFT JOIN (SELECT COUNT(*) AS MENU_COUNT, B.CATEGORY_CODE "
                + " FROM TBL_MENU B "
                + " GROUP BY B.CATEGORY_CODE) V ON (A.CATEGORY_CODE = V.CATEGORY_CODE)"
                + " ORDER BY 1";

        Query nativeQuery = entityManager.createNativeQuery(query, "categoryCountManualMapping");
        List<Object[]> categoryList = nativeQuery.getResultList();
        //then
        assertNotNull(categoryList);
        assertTrue(entityManager.contains(categoryList.get(0)[0])); //영속석컨텍스트에서 관리되는지 확인 코드
        categoryList.forEach(row -> { //출력 : Category{categoryCode=6, categoryName='일식', refCategoryCode=1} 4
            Stream.of(row).forEach(col -> System.out.print(col + " "));
            System.out.println();
        });
    }
}