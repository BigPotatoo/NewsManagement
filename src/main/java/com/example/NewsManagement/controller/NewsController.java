package com.example.NewsManagement.controller;

import com.example.NewsManagement.model.News;
import com.example.NewsManagement.model.UserAccount;
import com.example.NewsManagement.service.NewsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@Transactional
@RequestMapping("/api/v1/news")
public class NewsController {
    NewsService newsService;

    @GetMapping
    public Page<News> list(@PageableDefault(sort = "id") Pageable pageable) {
        return newsService.findAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'journalist')")
    @PostMapping
    public News add(@Valid @RequestBody News news,
                    @AuthenticationPrincipal UserAccount userAccount) {
        return newsService.save(news, userAccount);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'journalist')")
    @PutMapping("{newsId}")
    public News update(@PathVariable("newsId") Long id,
                       @Valid @RequestBody News news,
                       @AuthenticationPrincipal UserAccount userAccount) {
        return newsService.update(id, news, userAccount);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'journalist')")
    @DeleteMapping("{newsId}")
    public ResponseEntity<?> delete(@PathVariable("newsId") Long newsId,
                                    @AuthenticationPrincipal UserAccount userAccount) {
        return newsService.delete(newsId, userAccount);
    }
}
