package com.louis.shopsecurity.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class Category {
    private Integer id;              // 分類ID
    private String name;             // 分類名字
    private Boolean root;            // 是否根分類
    private Integer parentId;        // 父分類ID
    private List<Category> children; // 子分類列表
}