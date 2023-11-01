package com.ohgiraffers.section06.compositekey.subsection01.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// 복합기 클래스는 반드시 직렬화 처리(implements Serializable), equals 및 hashcode 오버라이딩
@Embeddable //이 클래스가 포함될수 있다는 표시 어노테이션
public class MemberPK implements Serializable {
    @Column(name="member_no")
    private int memberNo;
    @Column(name="member_id")
    private String memberId;

    public MemberPK() {}

    public MemberPK(int memberNo, String memberId) {
        this.memberNo = memberNo;
        this.memberId = memberId;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "MemberPk{" +
                "memberNo=" + memberNo +
                ", memberId='" + memberId + '\'' +
                '}';
    }

    //equals() 및 hashCode() 자동 생성
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberPK memberPk = (MemberPK) o;
        return memberNo == memberPk.memberNo && Objects.equals(memberId, memberPk.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberNo, memberId);
    }
}
