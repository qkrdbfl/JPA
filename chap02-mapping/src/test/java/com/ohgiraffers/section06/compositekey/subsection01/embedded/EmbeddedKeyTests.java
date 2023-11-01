package com.ohgiraffers.section06.compositekey.subsection01.embedded;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmbeddedKeyTests {

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

    @AfterAll
    public static void closeFactory(){
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager(){
        entityManager.close();
    }


    @Test
    public void 임베디드_아이디_사용한_복합키_테이블_매핑_테스트() {
        //given
        Member member = new Member();
        member.setMemberPK(new MemberPK(1, "user01"));
        member.setPhone("010-1234-5678");
        member.setAddress("서울시 종로구");
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member);
        entityTransaction.commit();
        //then
        Member foundMember = entityManager.find(Member.class, member.getMemberPK());
        assertEquals(member.getMemberPK(), foundMember.getMemberPK());
    }

}
