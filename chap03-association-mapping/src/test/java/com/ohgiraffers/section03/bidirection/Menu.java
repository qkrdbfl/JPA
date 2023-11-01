package com.ohgiraffers.section03.bidirection;

import javax.persistence.*;

@Entity(name="bidirection_menu")
@Table(name="TBL_MENU")
public class Menu { //메뉴 테이블이 연관관계의 주인임.

    @Id
    private int menuCode;
    private String menuName;
    private int menuPrice;

    @ManyToOne()
    @JoinColumn(name = "categoryCode") //연관관계 주인쪽에 쓰기
    private Category category; //메뉴에서 카테고리를 참조함 (양방향 참조) //누가 연관관계의 주인인지 알것.(-> FK를 가지고 있는 테이블)
    private String OrderableStatus;

    public Menu() {
    }

    public Menu(int menuCode, String menuName, int menuPrice, Category category, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        OrderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getOrderableStatus() {
        return OrderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        OrderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", OrderableStatus='" + OrderableStatus + '\'' +
                '}';
    }
}
