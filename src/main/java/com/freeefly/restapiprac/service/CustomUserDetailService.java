package com.freeefly.restapiprac.service;

import com.freeefly.restapiprac.advice.exception.UserNotFoundException;
import com.freeefly.restapiprac.config.CacheKey;
import com.freeefly.restapiprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Cacheable(value = CacheKey.USER, key = "#userPk", unless = "#result == null")
    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(userPk)).orElseThrow(() -> new UserNotFoundException());
    }

}
