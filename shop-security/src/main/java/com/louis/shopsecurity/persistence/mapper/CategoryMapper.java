package com.louis.shopsecurity.persistence.mapper;

import com.louis.shopsecurity.persistence.entity.Category;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 找到所有根分類及其後代分類
     *
     * @return 根分類的列表
     */
    @Results(id = "categoryMap", value = {
            @Result(id = true, property = "id", column = "id") , @Result(property = "parentId", column = "parent_id") ,
            @Result(property = "children", column = "id", many = @Many(select = "findChildrenByParentId", fetchType = FetchType.EAGER))
    })
    @Select("select * from category where root = 1")
    List<Category> findAll ();

    /**
     * 查詢某個分類的所有子分類
     *
     * @param parentId 父分類的ID
     * @return 子分類列表
     */
    @ResultMap("categoryMap")
    @Select("select * from category where parent_id = #{parentId}")
    List<Category> findChildrenByParentId (int parentId);

    /**
     * 根據ID查詢某個分類
     *
     * @param id 分類ID
     * @return
     */
    @Results({
            @Result(id = true, property = "id", column = "id") , @Result(property = "parentId", column = "parent_id")
    })
    @Select("select id, name, root,  parent_id from category where id = #{id}")
    Category findById (int id);
}