package com.freeefly.restapiprac.controller.v1;

import com.freeefly.restapiprac.common.BaseTest;
import com.freeefly.restapiprac.entity.User;
import com.freeefly.restapiprac.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("정상적으로 유저를 가져오는지 확인")
    public void getUsers () throws Exception {
        // given
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .uid("user" + i + "email.com")
                    .name("user" + i)
                    .build();
            userRepository.save(user);
        }
        // when & then
        String contentAsString = mockMvc.perform(get("/v1/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

}