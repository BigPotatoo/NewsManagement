package com.example.NewsManagement.repository;

import com.example.NewsManagement.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"userAccount.roles", "news"})
    Page<Comment> findByNewsId(Long postId, Pageable pageable);

    Optional<Comment> findByIdAndNewsId(Long id, Long newsId);
}
