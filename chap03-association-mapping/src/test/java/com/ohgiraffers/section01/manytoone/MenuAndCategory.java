package com.ohgiraffers.section01.manytoone;

import javax.persistence.*;

@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
public class MenuAndCategory {
    @Id
    private int menuCode;
    private String menuName;
    private int menuPrice;

    //(cascade = CascadeType.PERSIST) : 저장하는 상태(PERSIST)에서 영속성 상태를 전이시킨다.
    @ManyToOne(cascade = CascadeType.PERSIST) // 다중성에 따라서 그에 맞는 어노테이션 적기
    @JoinColumn(name = "categoryCode")//()안에 FK에 해당하는 컬럼명이 들어가야한다. 조인할떄 쓰는 컬럼이름 명시 꼭 하기.
    private Category category;
    private String orderableStatus;

    public MenuAndCategory() {
    }

    public MenuAndCategory(int menuCode, String menuName, int menuPrice, Category category, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
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
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "MenuAndCategory{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
