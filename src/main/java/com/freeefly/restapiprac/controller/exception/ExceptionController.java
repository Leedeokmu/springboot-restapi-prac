package com.freeefly.restapiprac.controller.exception;

import com.freeefly.restapiprac.advice.exception.AuthenticationEntryPointException;
import com.freeefly.restapiprac.model.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public CommonResult entryPointException() {
        throw new AuthenticationEntryPointException();
    }

    @GetMapping("/accessdenied")
    public CommonResult accessDeniedException() {
        throw new AccessDeniedException("access denied");
    }

}
