package com.example.NewsManagement.service.impl;

import com.example.NewsManagement.exception.ResourceNotFoundException;
import com.example.NewsManagement.model.Comment;
import com.example.NewsManagement.model.News;
import com.example.NewsManagement.model.UserAccount;
import com.example.NewsManagement.repository.CommentRepo;
import com.example.NewsManagement.repository.NewsRepo;
import com.example.NewsManagement.service.CommentService;
import com.example.NewsManagement.service.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CommentServiceImpl implements CommentService {

    CommentRepo commentRepo;
    NewsRepo newsRepo;
    UtilService utilService;

    @Override
    public Comment save(Long newsId, Comment comment, UserAccount userAccount) {
        return utilService.checkAccessForComment(newsId, comment, userAccount).map(news -> {
            comment.setNews(news);
            return commentRepo.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("NewsId " + newsId + " not found"));
    }

    @Override
    public Comment update(Long newsId, Long commentId, Comment commentRequest, UserAccount userAccount) {
        if (!newsRepo.existsById(newsId)) {
            throw new ResourceNotFoundException("NewsId " + newsId + " not found");
        }

        utilService.checkAccessForComment(newsId, commentRequest, userAccount);

        return commentRepo.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            comment.setUserAccount(comment.getUserAccount());
            return commentRepo.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepo.findAll(pageable);
    }

    @Override
    public Page<Comment> findByNewsId(Long newsId, Pageable pageable) {
        return commentRepo.findByNewsId(newsId, pageable);
    }

    @Override
    public ResponseEntity<Object> delete(Long newsId, Long commentId, UserAccount userAccount) {
        News newsById = newsRepo.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsId " + newsId + " not found"));
        Comment commentById = commentRepo.findByIdAndNewsId(commentId, newsId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " for newsId " + newsId));
        return utilService.checkAccessForComment(newsById.getId(), commentById, userAccount)
                .map(news -> {
                    commentRepo.delete(commentById);
                    return ResponseEntity.noContent().build();
                }).orElseThrow();
    }
}