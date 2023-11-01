package com.ohgiraffers.section04.onetoone.subsection01;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "user_info1")
@Table(name = "tbl_user_info1")
public class UserInfo { //Fk

    @Id
    private  Long userInfoCode;
    private String nickname;
    private String phone;

    public UserInfo() {
    }

    public UserInfo(Long userInfoCode, String nickname, String phone) {
        this.userInfoCode = userInfoCode;
        this.nickname = nickname;
        this.phone = phone;
    }

    public Long getUserInfoCode() {
        return userInfoCode;
    }

    public void setUserInfoCode(Long userInfoCode) {
        this.userInfoCode = userInfoCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userInfoCode=" + userInfoCode +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
