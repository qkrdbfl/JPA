package com.ohgiraffers.section02.crud;

import javax.persistence.*;

@Entity(name = " section02_menu") //다름 패키지에 동일 이름의 패키지가 존재하면 오류가 나서 구분하기 위해 이름 지정함
@Table(name = "TBL_MENU")
@SequenceGenerator(
        name = "seq_menu_code_generator", //여기부터
        sequenceName = "SEQ_MENU_CODE",
        initialValue = 100,
        allocationSize = 1 //여기까지 이건 DB 설정임
)
public class Menu {

    @Id
    @Column(name = "MENU_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_menu_code_generator" //위와 이름 동일하게 지칭해줌 GeneratedValue를 쓸때 위의 설정을 쓰겠다는 의미인가?
    )
    private int menuCode;

    @Column(name = "MENU_NAME")
    private String menuName;

    @Column(name = "MENU_PRICE")
    private int menuPrice;

    @Column(name = "CATEGORY_CODE")
    private int categoryCode;

    @Column(name = "ORDERABLE_STATUS")
    private String orderableStatus;

    public Menu() {
    }

    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
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

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode='" + categoryCode + '\'' +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
