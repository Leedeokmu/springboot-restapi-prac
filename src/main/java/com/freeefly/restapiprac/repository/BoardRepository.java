package com.freeefly.restapiprac.repository;

import com.freeefly.restapiprac.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByName(String name);
}
