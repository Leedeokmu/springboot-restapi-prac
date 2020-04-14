package com.freeefly.restapiprac.repository;

import com.freeefly.restapiprac.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 정보를 정상적으로 저장하는지 테스트")
    public void saveUserTest () {
        // given
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String uid = "test@email.com";
        String password = "test";
        String name = "test";
        User user = User.builder()
                .uid(uid)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
        // when
        userRepository.save(user);
        testEntityManager.flush();
        testEntityManager.clear();
        User foundUser = userRepository.findByUid(uid).get();

        // then
        assertNotNull(foundUser);
        assertEquals(name, foundUser.getName());
        assertTrue(passwordEncoder.matches(password, foundUser.getPassword()));
    }

}