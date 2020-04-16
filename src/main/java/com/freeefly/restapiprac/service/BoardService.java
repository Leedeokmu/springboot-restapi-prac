package com.freeefly.restapiprac.service;

import com.freeefly.restapiprac.advice.exception.NotOwnerException;
import com.freeefly.restapiprac.advice.exception.ResourceNotExistsException;
import com.freeefly.restapiprac.advice.exception.UserNotFoundException;
import com.freeefly.restapiprac.entity.Board;
import com.freeefly.restapiprac.entity.Post;
import com.freeefly.restapiprac.entity.User;
import com.freeefly.restapiprac.model.ParamsPost;
import com.freeefly.restapiprac.repository.BoardRepository;
import com.freeefly.restapiprac.repository.PostRepository;
import com.freeefly.restapiprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Board findBoard(String boardName) {
        return Optional.ofNullable(boardRepository.findByName(boardName)).orElseThrow(ResourceNotExistsException::new);
    }

    public List<Post> findPosts(String boardName) {
        return postRepository.findByBoard(this.findBoard(boardName));
    }

    public Post getPost(Long postId){
        return postRepository.findById(postId).orElseThrow(ResourceNotExistsException::new);
    }

    @Transactional
    public Post writePost(String uid, String boardName, ParamsPost paramsPost) {
        Board board = boardRepository.findByName(boardName);
        User user = userRepository.findByUid(uid).orElseThrow(UserNotFoundException::new);
        Post post = new Post(user, board, paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(long postId, String uid, ParamsPost paramsPost) {
        Post post = getPost(postId);
        User user = post.getUser();
        if (!uid.equals(user.getUid())) {
            throw new NotOwnerException();
        }
        post.setUpdate(paramsPost.getAuthor(), paramsPost.getTitle(), paramsPost.getContent());
        return post;
    }
    @Transactional
    public boolean deletePost(long postId, String uid) {
        Post post = getPost(postId);
        User user = post.getUser();
        if (!uid.equals(user.getUid())){
            throw new NotOwnerException();
        }
        postRepository.delete(post);
        return true;
    }


}
