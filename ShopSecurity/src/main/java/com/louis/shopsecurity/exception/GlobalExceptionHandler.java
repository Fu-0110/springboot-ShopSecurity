package com.louis.shopsecurity.exception;

import com.louis.shopsecurity.controller.result.BaseResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 處理前端請求參數錯誤例外
     *
     * @param e
     * @return BaseResult
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResult handleAllExceptions (MissingServletRequestParameterException e) {
        BaseResult result = new BaseResult();
        result.setCode(HttpStatus.BAD_REQUEST.value());
        result.setMsg(e.getMessage());
        return result;
    }

    /**
     * 處理 404 錯誤
     *
     * @param e
     * @return BaseResult
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResult handleNoHandlerError (NoHandlerFoundException e) {
        BaseResult result = new BaseResult();
        result.setCode(HttpStatus.NOT_FOUND.value());
        result.setMsg("您請求的資源不存在，或者已經移動到其他位置，請確認存取的URL");
        return result;
    }

    /**
     * 處理伺服器內部錯誤
     *
     * @param e
     * @return BaseResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResult handleInternalServerExceptions (Exception e) {
        BaseResult result = new BaseResult();
        result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.setMsg("伺服器暫時不能為您服務，請聯繫管理員");
        return result;
    }
}
