package com.freeefly.restapiprac.advice;

import com.freeefly.restapiprac.advice.exception.UserNotFountException;
import com.freeefly.restapiprac.model.CommonResult;
import com.freeefly.restapiprac.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;

    @ExceptionHandler(UserNotFountException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFountExceptionHandler(HttpServletRequest request, UserNotFountException e) {
        log.error("[userNotFountExceptionHandler]");
        return responseService.getFailResult();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultExceptionHandler(HttpServletRequest request, Exception e) {
        return responseService.getFailResult();
    }
}
