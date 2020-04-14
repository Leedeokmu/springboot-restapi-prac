package com.freeefly.restapiprac.advice;

import com.freeefly.restapiprac.advice.exception.AuthenticationEntryPointException;
import com.freeefly.restapiprac.advice.exception.EmailSigninFailedException;
import com.freeefly.restapiprac.advice.exception.UserNotFoundException;
import com.freeefly.restapiprac.config.MessageSourceUtils;
import com.freeefly.restapiprac.model.CommonResult;
import com.freeefly.restapiprac.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;
    private final MessageSourceUtils messageSourceUtils;

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFountExceptionHandler(HttpServletRequest request, UserNotFoundException e) {
        log.error("[userNotFountExceptionHandler]");
        return responseService.getFailResult(
                Integer.valueOf(messageSourceUtils.getMessage("userNotFound.code")),
                messageSourceUtils.getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(EmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailedExceptionHandler(HttpServletRequest request, EmailSigninFailedException e) {
        log.error("[userNotFountExceptionHandler]");
        return responseService.getFailResult(
                Integer.valueOf(messageSourceUtils.getMessage("emailSigninFailed.code")),
                messageSourceUtils.getMessage("emailSigninFailed.msg"));
    }

    @ExceptionHandler(AuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntryPointExceptionHandler(HttpServletRequest request, AuthenticationEntryPointException e) {
        log.error("[userNotFountExceptionHandler]");
        return responseService.getFailResult(
                Integer.valueOf(messageSourceUtils.getMessage("entryPointException.code")),
                messageSourceUtils.getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedExceptionHandler(HttpServletRequest request, AccessDeniedException e) {
        log.error("[userNotFountExceptionHandler]");
        return responseService.getFailResult(
                Integer.valueOf(messageSourceUtils.getMessage("accessDenied.code")),
                messageSourceUtils.getMessage("accessDenied.msg"));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultExceptionHandler(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(
                Integer.valueOf(messageSourceUtils.getMessage("unKnown.code")),
                messageSourceUtils.getMessage("unKnown.msg"));
    }
}
