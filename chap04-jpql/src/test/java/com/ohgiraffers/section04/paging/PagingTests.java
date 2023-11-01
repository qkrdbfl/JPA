package com.ohgiraffers.section04.paging;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PagingTests {

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
    public void  페이징_API를_이용한_조회_테스트(){
        //given
        int offset = 10; //건너뛰고자 하는 행의 개수  -> 스타트 11 ~ 엔드 15
        int limit = 5; // 조회하고 싶은 행의 개수
        //when
        String jpql = "SELECT m FROM menu_section04 m ORDER BY m.menuCode DESC";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setFirstResult(offset)//조회 시작 위치(11 ~ )
                .setMaxResults(limit) // 최대한 마지막 행까지 가져온다는 의미
                .getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
