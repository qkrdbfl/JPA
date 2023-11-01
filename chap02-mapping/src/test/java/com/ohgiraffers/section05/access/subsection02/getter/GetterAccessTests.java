package com.ohgiraffers.section05.access.subsection02.getter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetterAccessTests {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach // 펙토리 실행전 1번만 쓰임
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }


    //    @Test
//    public void 프로퍼티_접근_테스트() {
//        //given
//        Member member = new Member();
//        member.setMemberNo(1);
//        member.setMemberId("user01");
//        member.setMemberPwd("pass01");
//        member.setNickname("홍길동");
//        //when
//        entityManager.persist(member);// 해당 객체 저장
//        //then
//       Member foundMember = entityManager.find(Member.class, member.getMemberNo());
//        assertEquals(member.getMemberNo(), foundMember.getMemberNo());
//        System.out.println("foundMember = " + foundMember);
//    }

    @Test
    public void 프로퍼티_접근_테스트() {
//given
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member);// 해당 객체 저장
        entityTransaction.commit();
        //then
        String jpql = "SELECT A.nickname FROM member_section05_subsection02 A WHERE A.memberNo = 1";
        String registedNickname = entityManager.createQuery(jpql, String.class).getSingleResult();
        assertEquals("홍길동님", registedNickname);
    }


}
