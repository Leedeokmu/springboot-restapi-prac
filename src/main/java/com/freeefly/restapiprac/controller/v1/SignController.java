package com.freeefly.restapiprac.controller.v1;

import com.freeefly.restapiprac.advice.exception.EmailSigninFailedException;
import com.freeefly.restapiprac.config.security.JwtTokenProvider;
import com.freeefly.restapiprac.entity.User;
import com.freeefly.restapiprac.entity.UserRole;
import com.freeefly.restapiprac.model.CommonResult;
import com.freeefly.restapiprac.model.SingleResult;
import com.freeefly.restapiprac.repository.UserRepository;
import com.freeefly.restapiprac.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class SignController {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(
            @ApiParam(value = "회원ID(이메일)", required = true) @RequestParam(value = "id") String id,
            @ApiParam(value = "비밀번호", required = true) @RequestParam(value = "password") String password
    ) {
        User user = userRepository.findByUid(id).orElseThrow(EmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new EmailSigninFailedException();
        }

        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(
            @ApiParam(value = "회원ID(이메일)", required = true) @RequestParam(value = "id") String id,
            @ApiParam(value = "비밀번호", required = true) @RequestParam(value = "password") String password,
            @ApiParam(value = "이름", required = true) @RequestParam(value = "name") String name
    ) {
        userRepository.save(User.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .roles(new ArrayList<UserRole>() {{
                    add(UserRole.USER);
                }})
                .build()
        );

        return responseService.getSuccessResult();
    }
}
