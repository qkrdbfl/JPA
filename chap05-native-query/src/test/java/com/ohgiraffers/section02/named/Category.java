
package com.ohgiraffers.section02.named;

import javax.persistence.*;

@Entity(name = "category_section02")
@Table(name = "tbl_category")
@SqlResultSetMapping(
        name = "categoryCountAutoMapping2",
        entities = {@EntityResult(entityClass = com.ohgiraffers.section01.simple.Category.class)},//카테고리 엔티티로 매핑 하겠단 의미
        columns = {@ColumnResult(name = "MENU_COUNT")} //이 컬럼을 매핑하겠다는 정의
)
@NamedNativeQueries(
        value = {
                @NamedNativeQuery(
                        name = "Category.menuCountOfCategory",
                        query = "SELECT"
                                + " A.CATEGORY_CODE, A.CATEGORY_NAME, A.REF_CATEGORY_CODE, COALESCE(V.MENU_COUNT, 0) MENU_COUNT "
                                + " FROM TBL_CATEGORY A "
                                + " LEFT JOIN (SELECT COUNT(*) AS MENU_COUNT, B.CATEGORY_CODE "
                                + " FROM TBL_MENU B "
                                + " GROUP BY B.CATEGORY_CODE) V "
                                + " ON (A.CATEGORY_CODE = V.CATEGORY_CODE)"
                                + " ORDER BY 1",
                        resultSetMapping = "categoryCountAutoMapping2" //결과 처리 매핑
                )
        }
)
public class Category {

    @Id
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;

    public Category() {
    }

    public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
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

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}
