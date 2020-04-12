package com.freeefly.restapiprac.repository;

import com.freeefly.restapiprac.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
