package com.example.NewsManagement.service.impl;

import com.example.NewsManagement.exception.AccessDeniedException;
import com.example.NewsManagement.model.Comment;
import com.example.NewsManagement.model.News;
import com.example.NewsManagement.model.UserAccount;
import com.example.NewsManagement.repository.NewsRepo;
import com.example.NewsManagement.service.UtilService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UtilServiceImpl implements UtilService {

    NewsRepo newsRepo;

    @Override
    public Optional<News> checkAccessForNews(Long newsId, UserAccount userAccount) {
        Optional<News> byId = newsRepo.findById(newsId);

        byId.map(n -> {
            if (n.getUserAccount().getId().equals(userAccount.getId()) ||
                    userAccount.getRoles().getName().equals("admin")) {
                return byId;
            } else {
                throw new AccessDeniedException("Access denied!");
            }
        });
        return byId;
    }

    @Override
    public Optional<News> checkAccessForComment(Long newsId, Comment comment, UserAccount userAccount) {
        Optional<News> byId = newsRepo.findById(newsId);

        byId.map(n -> {
            if (userAccount.getId().equals(comment.getUserAccount().getId()) ||
                    userAccount.getRoles().getName().equals("admin")) {
                return byId;
            } else {
                throw new AccessDeniedException("Access denied!");
            }
        });
        return byId;
    }
}
