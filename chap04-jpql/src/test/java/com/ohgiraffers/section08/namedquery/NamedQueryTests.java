package com.ohgiraffers.section08.namedquery;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NamedQueryTests {

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
    public void 동적쿼리를_이용한_조회_테스트() {
        //given
        String searchName = "한우"; //빈 문자로 하면 카테고리 코드만 왜얼절에 붙음
        int searchCategoryCode = 4; //0으로 하면 네임만 왜얼절에 붙음 -> (둘다 비우면 왜얼절에 암것도 안붙어서 전체 조회됨)
        //when
        StringBuilder jpql = new StringBuilder("SELECT m FROM menu_section08 m ");
        if(searchName != null && !searchName.isEmpty() && searchCategoryCode > 0){
            jpql.append("WHERE ");
            jpql.append("m.menuName LIKE '%' || : menuName || '%' ");
            jpql.append("AND ");
            jpql.append("m.categoryCode = :categoryCode "); //뒤에 이어쓸때 한칸씩 띄어 줘야함. 붙게 인식해서 오류날수 있음
        }else {
            if(searchName != null && !searchName.isEmpty()) {
                jpql.append("WHERE ");
                jpql.append("m.menuName LIKE '%' || : menuName || '%' ");
            }else if(searchCategoryCode > 0){
                jpql.append("WHERE ");
                jpql.append("m.categoryCode = :categoryCode ");
            }
        }
        TypedQuery<Menu> query = entityManager.createQuery(jpql.toString(), Menu.class);
        if(searchName != null && !searchName.isEmpty() && searchCategoryCode > 0){
            query.setParameter("menuName", searchName);
            query.setParameter("categoryCode", searchCategoryCode);
        }else {
            if (searchName != null && !searchName.isEmpty()) {
                query.setParameter("menuName", searchName);
            } else if (searchCategoryCode > 0) {
                query.setParameter("categoryCode", searchCategoryCode);
            }
        }
        List<Menu> menuList = query.getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }

    @Test
    public void 네임드쿼리를_이용한_조회_테스트() {
        //when
        List<Menu> menuList = entityManager.createNamedQuery("menu_section08.selectMenuList", Menu.class) //()안에 네임드에 정의했던대로 적기
                .getResultList(); //실행하는 메소드 호출
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
