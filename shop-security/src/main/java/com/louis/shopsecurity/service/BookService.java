package com.louis.shopsecurity.service;

import com.louis.shopsecurity.persistence.entity.Book;
import com.louis.shopsecurity.persistence.entity.BookDetail;
import com.louis.shopsecurity.persistence.entity.Comment;
import com.louis.shopsecurity.persistence.mapper.BookMapper;
import com.louis.shopsecurity.persistence.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CommentMapper commentMapper;

    public Book getBookById (int id) {
        return bookMapper.findById(id);
    }

    public List<Book> getBooksByHot () {
        return bookMapper.findBooksByHot();
    }

    public List<Book> getBooksByNew () {
        return bookMapper.findBooksByNew();
    }

    public BookDetail getBook (int id) {
        return bookMapper.findById(id);
    }

    public List<Book> getCategoryBooksByPage (int categoryId , int pageNum , int pageSize) {
        return bookMapper.findCategoryBooksByPage(categoryId , pageNum , pageSize);
    }

    public List<Book> getKeywordBooksByPage (String keyword , int pageNum , int pageSize) {
        return bookMapper.findKeywordBooksByPage(keyword , pageNum , pageSize);
    }

    public List<Comment> getCommentsByBookId (int bookId) {
        return commentMapper.findByBookId(bookId);
    }
}