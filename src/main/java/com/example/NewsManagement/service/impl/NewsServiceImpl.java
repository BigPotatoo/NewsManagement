package com.example.NewsManagement.service.impl;

import com.example.NewsManagement.exception.ResourceNotFoundException;
import com.example.NewsManagement.model.News;
import com.example.NewsManagement.model.UserAccount;
import com.example.NewsManagement.repository.NewsRepo;
import com.example.NewsManagement.service.NewsService;
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
public class NewsServiceImpl implements NewsService {

    NewsRepo newsRepo;

    UtilService utilService;

    @Override
    public News save(News news, UserAccount userAccount) {
        utilService.checkAccessForNews(news.getId(), userAccount);
        return newsRepo.save(news);
    }

    @Override
    public News update(Long newsId, News news, UserAccount userAccount) {
        return utilService.checkAccessForNews(newsId, userAccount).map(n -> {
            n.setTitle(news.getTitle());
            n.setText(news.getText());
            return newsRepo.save(n);
        }).orElseThrow(() -> new ResourceNotFoundException("NewsId " + newsId + " not found"));
    }

    @Override
    public Page<News> findAll(Pageable pageable) {
        return newsRepo.findAll(pageable);
    }

    @Override
    public ResponseEntity<Object> delete(Long newsId, UserAccount userAccount) {
        return utilService.checkAccessForNews(newsId, userAccount).map(news -> {
            newsRepo.delete(news);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new ResourceNotFoundException("NewsId " + newsId + " not found"));
    }
}
