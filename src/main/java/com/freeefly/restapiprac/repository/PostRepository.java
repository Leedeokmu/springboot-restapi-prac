package com.freeefly.restapiprac.repository;

import com.freeefly.restapiprac.entity.Board;
import com.freeefly.restapiprac.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBoard(Board board);
}
