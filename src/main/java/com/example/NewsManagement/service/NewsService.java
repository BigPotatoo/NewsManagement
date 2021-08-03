package com.example.NewsManagement.service;

import com.example.NewsManagement.model.News;
import com.example.NewsManagement.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface NewsService {
    News save(News news, UserAccount userAccount);

    News update(Long newsId, News newsRequest, UserAccount userAccount);

    Page<News> findAll(Pageable pageable);

    ResponseEntity<Object> delete(Long newsId, UserAccount userAccount);
}