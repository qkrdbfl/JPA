package com.ohgiraffers.section03.bidirection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity(name="bidirection_category")
@Table(name="TBL_CATEGORY")
public class Category { //카테고리는 PK라서 연관관계의 주인이 아님.

    @Id
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;

    @OneToMany(mappedBy = "category")//참조 필드의 필드명 작성하기.(진짜 연관관계가 누구인지.)
    //가짜연관관계인 쪽에는 (mappedBy) 작성. 진짜연관관계에는 (ManyToOne)(JoinColumn) 작성.
    private List<Menu> menuList; //카테고리 쪽에서 메뉴를 참조한다(양방향 참조)//누가 연관관계의 주인인지 알것.

    public Category() {
    }

    public Category(int categoryCode, String categoryName, Integer refCategoryCode, List<Menu> menuList) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
        this.menuList = menuList;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
//                ", menuList=" + menuList + //주인이 아닌 쪽에서 지워야 함
                '}';
    }
}


