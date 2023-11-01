package com.ohgiraffers.section01.simple;

import org.junit.jupiter.api.*;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SimpleJPQLTests {
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
    public void TypedQuery를_이용한_단일메뉴_조회_테스트(){
        //when
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class); //(실행할 구문, 반환받을 데이터 타입)
        String resultMenuName = query.getSingleResult(); //실행했울때 데이터 타입을 반환
        //then
        assertEquals("민트미역국", resultMenuName);
    }

    @Test
    public void Query를_이용한_단일메뉴_조회_테스트(){
        //when
        String jpql = "SELECT m.menuName FROM menu_section01 as m WHERE m.menuCode = 7";
        Query query = entityManager.createQuery(jpql);
        Object resultMenuName = query.getSingleResult(); //실행할때 오브젝트를 반환한다고 적는다 (위의 구문과의 차이점)
        //then
        assertEquals("민트미역국", resultMenuName);
    }

    //---------------------------------------------------------\
    @Test
    public void  TypedQuery를_이용한_단일행_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section01 as m WHERE m.menuCode = 7"; //m별칭하나 쓰면 전체 쿼리를 가져온다는 뜻
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        Menu foundMenu = query.getSingleResult();
        //then
        assertEquals(7, foundMenu.getMenuCode());
        System.out.println(foundMenu);
    }

    @Test
    public void  TypedQuery를_이용한_다중행_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section01 as m"; //모든 구문 조회
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> foundMenuList = query.getResultList();
        //then
        assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println); //한줄씩 출력하기 위해 forEach를 씀
    }

    @Test
    public void  Query를_이용한_다중행_조회_테스트(){
        //when
        String jpql = "SELECT m FROM menu_section01 as m"; //모든 구문 조회
        Query query = entityManager.createQuery(jpql);
        List<Menu> foundMenuList = query.getResultList();
        //then
        assertNotNull(foundMenuList);
        foundMenuList.forEach(System.out::println); //한줄씩 출력하기 위해 forEach를 씀
    }

    @Test
    public void distinct를_이용한_중복제거_여러행_조회_테스트(){
        //when
        String jpql = "SELECT DISTINCT m.categoryCode FROM menu_section01 m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> categoryCodeList = query.getResultList();
        //then
        assertNotNull(categoryCodeList);
        categoryCodeList.forEach(System.out::println);
    }

    @Test
    public void in_연산자를_활용한_조회_테스트(){
        //카테고리 코드가 6이거나 10인  menu 조회

        //when
        String jpql = "SELECT m FROM menu_section01 m WHERE m.categoryCode IN (6, 10)";
//        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
//        List<Menu> menuList = query.getResultList();
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList(); // 위의 구문을 한줄로 줄일수 있음
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);

    }

    @Test
    public void Like_연산자를_활용한_조회_테스트(){
        //"마늘"이 메뉴이름으로 들어간 menu 조회

        //when
        String jpql = "SELECT m FROM menu_section01 m WHERE m.menuName LIKE '%마늘%'";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> menuList = query.getResultList();
        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
    }
}
