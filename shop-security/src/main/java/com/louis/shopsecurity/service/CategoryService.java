package com.louis.shopsecurity.service;

import com.louis.shopsecurity.persistence.entity.Category;
import com.louis.shopsecurity.persistence.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategories () {
        return categoryMapper.findAll();
    }

    public List<Category> getChildrenByParent (int parentId) {
        return categoryMapper.findChildrenByParentId(parentId);
    }

    public Category getCategoryById (int id) {
        return categoryMapper.findById(id);
    }
}
