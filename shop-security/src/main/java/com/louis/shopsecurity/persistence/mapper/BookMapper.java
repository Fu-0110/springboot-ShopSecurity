package com.louis.shopsecurity.persistence.mapper;

import com.louis.shopsecurity.persistence.entity.Book;
import com.louis.shopsecurity.persistence.entity.BookDetail;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface BookMapper {
    /**
     * 獲取熱門圖書
     *
     * @return 熱門圖書列表
     */
    @Results(id = "bookMap", value = {
            @Result(id = true, property = "id", column = "id") , @Result(property = "imgUrl", column = "img") ,
            @Result(property = "imgBigUrl", column = "img_big")
    })
    @Select("select id, title, author, price, discount, img, img_big, inventory from books where is_hot = 1 ")
    List<Book> findBooksByHot ();

    /**
     * 獲取所有新書
     *
     * @return 新書列表
     */
    @ResultMap("bookMap")
    @Select("select id, title, author, price, discount, img, img_big, inventory from books where is_new = 1 ")
    List<Book> findBooksByNew ();

    /**
     * 根據圖書ID查詢圖書
     *
     * @param id
     * @return 詳細的圖書物件
     */
    @Results(id = "bookDetailMap", value = {
            @Result(id = true, property = "id", column = "id") , @Result(property = "imgUrl", column = "img") ,
            @Result(property = "imgBigUrl", column = "img_big") , @Result(property = "newness", column = "is_new") ,
            @Result(property = "hot", column = "is_hot") ,
            @Result(property = "specialOffer", column = "is_special_offer") ,
            @Result(property = "category", column = "category_id", one = @One(select = "com.louis.shopsecurity.persistence.mapper.CategoryMapper.findById", fetchType = FetchType.EAGER))
    })
    @Select("select * from books where id = #{id}")
    BookDetail findById (int id);

    /**
     * 分頁查詢某個分類下的圖書
     *
     * @param categoryId 分類ID
     * @param pageNum    第幾頁
     * @param pageSize   每頁大小
     * @return 圖書列表
     */
    @ResultMap("bookMap")
    @Select("select id, title, author, price, discount, img, img_big, inventory, publish_date, book_concern, brief from books where category_id = #{categoryId} ")
    List<Book> findCategoryBooksByPage (
            int categoryId , @Param("pageNum") int pageNum , @Param("pageSize") int pageSize
    );

    /**
     * 根據關鍵字分頁查詢圖書
     *
     * @param keyword  關鍵字
     * @param pageNum  第幾頁
     * @param pageSize 每頁大小
     * @return 圖書列表
     */
    @ResultMap("bookMap")
    @Select("select id, title, author, price, discount, img, img_big, inventory, publish_date, book_concern, brief from books where title like '%${keyword}%' ")
    List<Book> findKeywordBooksByPage (
            String keyword , @Param("pageNum") int pageNum , @Param("pageSize") int pageSize
    );

    /**
     * 獲取某個分類下的所有圖書
     * @param categoryId 分類ID
     * @return 某個分類下的圖書列表
     */
    /*
    @ResultMap("bookMap")
    @Select("select id, title, author, price, discount, img, img_big, inventory, publish_date, book_concern, brief from books where category_id = #{categoryId} ")
    List<Book> findBooksByCategory(int categoryId);
    */

    /**
     * 根據關鍵字模糊查詢所有圖書
     * @param keyword 關鍵字
     * @return 圖書列表
     */
    /*
    @ResultMap("bookMap")
    @Select("select id, title, author, price, discount, img, img_big, inventory, publish_date, book_concern, brief from books where title like '%${keyword}%' ")
    List<Book> findBooksByKeyword(String keyword);
    */
}
