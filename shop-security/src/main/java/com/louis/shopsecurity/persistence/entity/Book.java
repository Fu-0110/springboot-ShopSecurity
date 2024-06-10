package com.louis.shopsecurity.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Book {
    private Integer id;             // 圖書ID
    private String title;           // 圖書標題
    private String author;          // 圖書作者
    private Float price;            // 圖書價格
    private Float discount;         // 圖書折扣
    private String bookConcern;     // 出版社
    private String imgUrl;          // 圖書封面小圖URL
    private String imgBigUrl;       // 圖書封面大圖URL
    private LocalDate publishDate;  // 出版日期
    private String brief;           // 圖書簡介
    private Integer inventory;      // 圖書庫存
}