package com.louis.shopsecurity.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class BookDetail extends Book {
    private String detail;            //圖書詳細介紹
    private Boolean newness;          //是否新書
    private Boolean hot;              //是否熱門圖書
    private Boolean specialOffer;     //是否特價
    private String slogan;            //圖書宣傳語
    private Category category;        //圖書所屬分類
}
