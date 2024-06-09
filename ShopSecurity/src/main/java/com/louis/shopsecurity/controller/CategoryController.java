package com.louis.shopsecurity.controller;

import com.louis.shopsecurity.controller.result.BaseResult;
import com.louis.shopsecurity.controller.result.DataResult;
import com.louis.shopsecurity.persistence.entity.Category;
import com.louis.shopsecurity.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<BaseResult> getAllCategories () {
        List<Category> categories = categoryService.getAllCategories();

        DataResult<List<Category>> result = new DataResult<>();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("成功");
        result.setData(categories);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResult> getCategoryById (@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            DataResult<Category> result = new DataResult<>();
            result.setCode(HttpStatus.OK.value());
            result.setMsg("成功");
            result.setData(category);
            return ResponseEntity.ok(result);
        }
        else {
            BaseResult result = new BaseResult(HttpStatus.BAD_REQUEST.value() , "参数不合法");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/parent/{id}")
    public ResponseEntity<BaseResult> getChildrenByParent (@PathVariable int id) {
        List<Category> categories = categoryService.getChildrenByParent(id);
        if (categories.size() > 0) {
            DataResult<List<Category>> result = new DataResult<>();
            result.setCode(HttpStatus.OK.value());
            result.setMsg("成功");
            result.setData(categories);
            return ResponseEntity.ok(result);
        }
        else {
            BaseResult result = new BaseResult(HttpStatus.BAD_REQUEST.value() , "参数不合法");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}