package com.ohgiraffers.section03.primarykey.subsection01.sequence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimarykeyMappingTests {

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
    public void 식별자_매핑_테스트() { ///////////////////////////
        //given
        Member member = new Member();
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
        member.setPhone("010-1234-5678");
        member.setAddress("서울시 종로구");
        member.setEnrollDate(new Date());
        member.setMemberRole("ROLE_MEMBER");
        member.setStatus("Y");

        Member member2 = new Member();
        member2.setMemberId("user02");
        member2.setMemberPwd("pass02");
        member2.setNickname("이순신");
        member2.setPhone("010-0000-5678");
        member2.setAddress("서울시 중랑구");
        member2.setEnrollDate(new Date());
        member2.setMemberRole("ROLE_MEMBER");
        member2.setStatus("Y");
        //when
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member);// 해당 객체 저장
        entityManager.persist(member2);// 해당 객체 저장
        entityTransaction.commit();
        //then
        //JPQL : 마이바티스, 오라클 각 조건에 맞게 바뀌어 사용되는 JPA의 문법이다.
        String jpql = "SELECT A.memberNo FROM member_section03_subsection01 A"; // SQL문과 좀 다르다.
                          //별칭.필드명  프롬  //member_section03_subsection01 : 엔티티명
        List<Integer> memberNoList = entityManager.createQuery(jpql, Integer.class).getResultList();// getResultList(); : 여러개의 값일때  getSingleResult(); : 하나의 값일때
        memberNoList.forEach(System.out::println);
    }




}
