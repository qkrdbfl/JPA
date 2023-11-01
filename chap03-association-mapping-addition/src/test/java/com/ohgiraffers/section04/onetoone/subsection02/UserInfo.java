package com.ohgiraffers.section04.onetoone.subsection02;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "user_info2")
@Table(name = "tbl_user_info2")
public class UserInfo { //Fk

    @Id
    private  Long userInfoCode;
    private String nickname;
    private String phone;

    @OneToOne(mappedBy = "userInfo")
    private User user;

    public UserInfo() {
    }

    public UserInfo(Long userInfoCode, String nickname, String phone, User user) {
        this.userInfoCode = userInfoCode;
        this.nickname = nickname;
        this.phone = phone;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userInfoCode=" + userInfoCode +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
//                ", user=" + user +
                '}';
    }
}
