package com.louis.shopsecurity.persistence.mapper;

import com.louis.shopsecurity.persistence.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select id, content, comment_date, username from comment where book_id = #{bookId}")
    List<Comment> findByBookId(int bookId);
}