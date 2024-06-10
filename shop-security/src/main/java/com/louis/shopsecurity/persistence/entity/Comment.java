package com.louis.shopsecurity.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
public class Comment {
    private Integer id;                  //評論ID
    private String content;              //評論內容
    private LocalDateTime commentDate;   //評論時間
    private Book book;                   //所屬圖書
    private String username;             //評論使用者名稱
}