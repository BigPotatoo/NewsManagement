package com.example.NewsManagement.service;

import com.example.NewsManagement.model.Comment;
import com.example.NewsManagement.model.News;
import com.example.NewsManagement.model.UserAccount;

import java.util.Optional;

public interface UtilService {
    Optional<News> checkAccessForNews(Long newsId, UserAccount userAccount);

    Optional<News> checkAccessForComment(Long newsId, Comment comment, UserAccount userAccount);
}