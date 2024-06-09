package com.louis.shopsecurity.controller;

import com.github.pagehelper.Page;
import com.louis.shopsecurity.controller.result.BaseResult;
import com.louis.shopsecurity.controller.result.DataResult;
import com.louis.shopsecurity.controller.result.PaginationResult;
import com.louis.shopsecurity.persistence.entity.Book;
import com.louis.shopsecurity.persistence.entity.Comment;
import com.louis.shopsecurity.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResult> getBook (@PathVariable int id) {
        Book book = bookService.getBook(id);
        if (book != null) {
            List<Book> books = new ArrayList<>();
            books.add(book);
            translateBookImgUrl(books);
            DataResult<Book> result = new DataResult<>();
            result.setCode(HttpStatus.OK.value());
            result.setMsg("成功");
            result.setData(book);
            return ResponseEntity.ok(result);
        }
        else {
            BaseResult result = new BaseResult(HttpStatus.BAD_REQUEST.value() , "参数不合法");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/hot")
    public ResponseEntity<BaseResult> getHotBooks () {
        List<Book> books = bookService.getBooksByHot();
        translateBookImgUrl(books);
        DataResult<List<Book>> result = new DataResult<>();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("成功");
        result.setData(books);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/new")
    public ResponseEntity<BaseResult> getNewBooks () {
        List<Book> books = bookService.getBooksByNew();
        translateBookImgUrl(books);
        DataResult<List<Book>> result = new DataResult<>();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("成功");
        result.setData(books);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<BaseResult> getCategoryBooks (
            @PathVariable int id , @RequestParam int pageNum , @RequestParam int pageSize
    ) {
        List<Book> books = bookService.getCategoryBooksByPage(id , pageNum , pageSize);
        return getPaginationResult(books);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResult> searchBooks (
            String wd , @RequestParam int pageNum , @RequestParam int pageSize
    ) {
        List<Book> books = bookService.getKeywordBooksByPage(wd , pageNum , pageSize);
        return getPaginationResult(books);
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<BaseResult> getBookComments (@PathVariable int id) {
        List<Comment> comments = bookService.getCommentsByBookId(id);

        DataResult result = new DataResult();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("成功");
        result.setData(comments);
        return ResponseEntity.ok(result);
    }

    /**
     * 建構分頁資料物件
     *
     * @param books 圖書列表
     * @return ResponseEntity對象
     */
    private ResponseEntity<BaseResult> getPaginationResult (List<Book> books) {
        long total = ((Page)books).getTotal();
        translateBookImgUrl(books);
        PaginationResult<List<Book>> result = new PaginationResult<List<Book>>();
        result.setCode(HttpStatus.OK.value());
        result.setMsg("成功");
        result.setData(books);
        result.setTotal(total);
        return ResponseEntity.ok(result);
    }

    /**
     * 對圖書封面小圖和大圖的URL進行轉換
     *
     * @param books 圖書列表
     */
    private void translateBookImgUrl (List<Book> books) {
        for (Book book : books) {
            book.setImgUrl(getServerInfo() + "/img/" + book.getImgUrl());
            book.setImgBigUrl(getServerInfo() + "/img/" + book.getImgBigUrl());
        }
    }

    /**
     * 得到後端程式的上下文路徑
     *
     * @return 上下文路徑
     */
    private String getServerInfo () {
        ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        StringBuffer sb = new StringBuffer();
        HttpServletRequest request = attrs.getRequest();
        sb.append(request.getContextPath());
        return sb.toString();
    }

}
