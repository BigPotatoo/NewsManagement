package com.example.NewsManagement.service;

import com.example.NewsManagement.model.Comment;
import com.example.NewsManagement.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    Comment save(Long newsId, Comment comment, UserAccount userAccount);

    Comment update(Long newsId, Long commentId, Comment commentRequest, UserAccount userAccount);

    Page<Comment> findAll(Pageable pageable);

    Page<Comment> findByNewsId(Long newsId, Pageable pageable);

    ResponseEntity<Object> delete(Long newsId, Long commentId, UserAccount userAccount);
}