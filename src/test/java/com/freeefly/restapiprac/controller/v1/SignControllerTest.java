package com.freeefly.restapiprac.controller.v1;

import com.freeefly.restapiprac.entity.User;
import com.freeefly.restapiprac.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SignControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 테스트")
    public void signinTest () throws Exception {
        // given
        String id = "test@test.com";
        String password = "test";
        String name = "test";
        User user = User.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
        userRepository.save(user);
        // when & then
        mockMvc.perform(post("/v1/signin")
                .param("id", id)
                .param("password", password)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
        ;
    }

    @Test
    @DisplayName("회원 가입 테스트")
    public void signupTest () throws Exception {
        // given
        String id = "test@test.com";
        String password = "test";
        String name = "test";
        // when & then
        mockMvc.perform(post("/v1/signup")
                .param("id", id)
                .param("password", password)
                .param("name", name)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
        ;
    }
    
    @Test
    @DisplayName("권한이 없는 유저는 api 호출 시 access deny 됨")
    @WithMockUser(username = "mockUser", roles = {"ADMIN"})
    public void accessDeniedUser () throws Exception {
        // when & then
        mockMvc.perform(get("/v1/users"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/exception/accessdenied"))
        ;
    }
}