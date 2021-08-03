package com.example.NewsManagement.controller;

import com.example.NewsManagement.model.Comment;
import com.example.NewsManagement.model.UserAccount;
import com.example.NewsManagement.service.CommentService;
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
@RequestMapping("/api/v1/news/{newsId}/comments")
public class CommentController {
    CommentService commentService;

    @GetMapping
    public Page<Comment> list(@PathVariable(value = "newsId") Long newsId,
                              @PageableDefault(sort = "id") Pageable pageable) {
        return commentService.findByNewsId(newsId, pageable);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'journalist', 'subscriber')")
    @PostMapping
    public Comment add(@PathVariable(value = "newsId") Long newsId,
                       @Valid @RequestBody Comment comment,
                       @AuthenticationPrincipal UserAccount userAccount) {
        return commentService.save(newsId, comment, userAccount);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'journalist', 'subscriber')")
    @PutMapping("{commentId}")
    public Comment update(@PathVariable(value = "newsId") Long newsId,
                          @PathVariable(value = "commentId") Long commentId,
                          @Valid @RequestBody Comment commentRequest,
                          @AuthenticationPrincipal UserAccount userAccount) {
        return commentService.update(newsId, commentId, commentRequest, userAccount);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'journalist', 'subscriber')")
    @DeleteMapping("{commentId}")
    public ResponseEntity<?> delete(@PathVariable(value = "newsId") Long newsId,
                                    @PathVariable(value = "commentId") Long commentId,
                                    @AuthenticationPrincipal UserAccount userAccount) {
        return commentService.delete(newsId, commentId, userAccount);
    }
}
