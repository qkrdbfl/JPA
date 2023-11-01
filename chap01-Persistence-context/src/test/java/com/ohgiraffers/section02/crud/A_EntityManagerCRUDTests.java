package com.ohgiraffers.section02.crud;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class A_EntityManagerCRUDTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach // 펙토리 실행전 1번만 쓰임
    public void initManager(){
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void 메뉴코드로_메뉴_조회_테스트(){
        //given
        int menuCode = 2;
        //when
        Menu foundMenu = entityManager.find(Menu.class, menuCode); //2번 메뉴 가져와라
        //then
        Assertions.assertNotNull(foundMenu);
        Assertions.assertEquals(menuCode, foundMenu.getMenuCode());
        System.out.println("foundMenu = " + foundMenu); //2번 메뉴 가져오는지 콘솔 확인
    }

    @Test
    public void 새로운메뉴추가테스트(){ //저장에 대한 설정하는 중
        //given
        Menu menu = new Menu();
        menu.setMenuName("JPA 테스트용 신규 메뉴");
        menu.setMenuPrice(5000);
        menu.setCategoryCode(4);
        menu.setOrderableStatus("Y");
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(menu); //위 트랜지션을 실행할 때 새 메뉴를 저장하겠다라는 뜻
            entityTransaction.commit(); //해당 내용 커밋
        }catch (Exception e){ //수동으로 캐치절 감싸줌
            entityTransaction.rollback(); //롤백 지정
            e.printStackTrace();
        }
        //then
        Assertions.assertTrue(entityManager.contains(menu)); //메뉴 잘 저장 됐는지?
    }

    @Test
    public void 메뉴이름수정테스트(){
        //given
        Menu menu = entityManager.find(Menu.class, 2); //일케 수정
        System.out.println("menu = " + menu);
        String menuNameToChange = "자갈치스무디";
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            menu.setMenuName(menuNameToChange); //위의 수정 메뉴 이름을 설정했을때
            entityTransaction.commit(); //커밋하게끔
        }catch (Exception e){
            entityTransaction.rollback();
            e.printStackTrace();
        }
        //then
        Assertions.assertEquals(menuNameToChange, entityManager.find(Menu.class, 2).getMenuName());//2번 프라이머리키를 찾아왓을때 바꿀이름과 같은지 여부 확인용
    }

    @Test
    public void 메뉴삭제하기테스트(){
        //given
        Menu menuToRemove = entityManager.find(Menu.class, 1);
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.remove(menuToRemove);//특정 엔티티 객체를 삭제해라 라는 명령어
            entityTransaction.commit(); //반영되게 커밋
        }catch (Exception e){
            entityTransaction.rollback();
            e.printStackTrace();
        }
        //then
        Menu removedMenu = entityManager.find(Menu.class, 1);
        Assertions.assertEquals(null, removedMenu); //1이 삭제되면 null이 되겠지 라고 예상한다는 코드임

    }

}
