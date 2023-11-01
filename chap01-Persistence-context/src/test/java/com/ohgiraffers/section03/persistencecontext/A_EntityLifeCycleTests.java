package com.ohgiraffers.section03.persistencecontext;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class A_EntityLifeCycleTests {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeAll //모든게 진행되기 전 딱 한 번 호출
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");  //persistence.xml에 persistence-unit name과 일치해야함
    }

    @BeforeEach // 테스트를 하기 전 마다 호출
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll //테스트가 끝나고 딱 한 번 호출
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach //테스트가 끝날 때 마다 호출
    public void closeManager() {
        entityManager.close();
    }

    @Test
    public void 비영속성_테스트() {
        //given
        Menu foundMenu = entityManager.find(Menu.class, 11);
        Menu newMenu = new Menu();
        newMenu.setMenuCode(foundMenu.getMenuCode());
        newMenu.setMenuName(foundMenu.getMenuName());
        newMenu.setMenuPrice(foundMenu.getMenuPrice());
        newMenu.setCategoryCode(foundMenu.getCategoryCode());
        newMenu.setOrderableStatus(foundMenu.getOrderableStatus());
        //when
        boolean isTrue = (foundMenu == newMenu);
        //then
        assertFalse(isTrue); //false 일 것이라고 예상
    }

    @Test
    public void 영속성연속조회테스트() {
        //given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 11);
        //when
        boolean isTrue = (foundMenu1 == foundMenu2);
        //then
        assertTrue(isTrue);
    }

    @Test
    public void 영속성_객체_추가_테스트() {
        //given
        Menu menuToRegist = new Menu();
        menuToRegist.setMenuCode(500);
        menuToRegist.setMenuName("수박죽");
        menuToRegist.setMenuPrice(10000);
        menuToRegist.setCategoryCode(1);
        menuToRegist.setOrderableStatus("Y");
        //when
        entityManager.persist(menuToRegist);
        Menu foundMenu = entityManager.find(Menu.class, 500);
        boolean isTrue = (menuToRegist == foundMenu);
        //then
        assertTrue(isTrue);
    }

    @Test
    public void 영속성_객체_추가_값_변경_테스트() {
        //given
        Menu menuToRegist = new Menu();
        menuToRegist.setMenuCode(500);
        menuToRegist.setMenuName("수박죽");
        menuToRegist.setMenuPrice(10000);
        menuToRegist.setCategoryCode(1);
        menuToRegist.setOrderableStatus("Y");
        //when
        entityManager.persist(menuToRegist);
        menuToRegist.setMenuName("메론죽"); //이름을 메론죽으로 바꾼다
        Menu foundMenu = entityManager.find(Menu.class, 500); // 여기 foundMenu는 메론죽이 된다
        //then
        assertEquals("메론죽", foundMenu.getMenuName());
    }

    @Test//실패구문
    public void 준영속성_detach_테스트(){
        //given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);
        //when
        entityManager.detach(foundMenu2); //detach : 관리 안함. 영속성에서 비영속성으로 바뀜
        foundMenu1.setMenuPrice(5000);
        foundMenu2.setMenuPrice(5000);
        //then
        assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice());//assertEquals : 같은지 물어보는 명령어
        assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice()); //오류

    }

    @Test //실패구문
    public void 준영속성_clear_테스트() {
        //given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);
        //when
        entityManager.clear(); //foundMenu1, foundMenu2를 클리어 (삭제)
        foundMenu1.setMenuPrice(5000);
        foundMenu2.setMenuPrice(5000);
        //then
        assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice()); //오류
        assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice());//오류
    }

    @Test//실패구문
    public void close_테스트() {
        //given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);
        //when
        entityManager.close(); //entityManager 이거 기능을 종료 시킴
        foundMenu1.setMenuPrice(5000);
        foundMenu2.setMenuPrice(5000);
        //then
        assertEquals(5000, entityManager.find(Menu.class, 11).getMenuPrice()); //오류
        assertEquals(5000, entityManager.find(Menu.class, 12).getMenuPrice()); ///오류
    }

    @Test
    public void 삭제_remove_테스트() {
        //given
        Menu foundMenu = entityManager.find(Menu.class, 2);
        //when
        entityManager.remove(foundMenu); //remove 하겠다는건? : 다시 찾아올수 없다. 관리될수 없다
        Menu refoundMenu = entityManager.find(Menu.class, 2); // 어차피 다시 불러올수가 없어서 여기서 이 구문은 필요 없음
        //then
        assertEquals(2, foundMenu.getMenuCode());
        assertEquals(null, refoundMenu);
    }

    @Test
    public void 병합_merge_수정_테스트(){
        //given
        Menu menuToDetach = entityManager.find(Menu.class, 2);
        entityManager.detach(menuToDetach);
        //when
        menuToDetach.setMenuName("수박죽");
        Menu refoundMenu = entityManager.find(Menu.class, 2);
        entityManager.merge(menuToDetach); //존재하는 값이 있으면 머지한다(병합)
        //then
        Menu mergedMenu = entityManager.find(Menu.class, 2);
        assertEquals("수박죽", mergedMenu.getMenuName());
    }

    @Test
    public void 병합_merge_삽입_테스트(){
        //given
        Menu menuToDetach = entityManager.find(Menu.class, 2);
        entityManager.detach(menuToDetach);
        //when
        menuToDetach.setMenuCode(999); //DB에서 조회 할수 없는 키값으로 변경해봄
        menuToDetach.setMenuName("수박죽");
        entityManager.merge(menuToDetach); //존재하는 값이 있으면 머지한다(병합) 하지만 존재하지 않는 값이면 삽입이 된다
        //then
        Menu mergedMenu = entityManager.find(Menu.class, 999);// 영속성 컨텍스트 안에 생성됨
        assertEquals("수박죽", mergedMenu.getMenuName());
    }

}


