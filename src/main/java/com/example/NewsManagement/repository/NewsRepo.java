package com.example.NewsManagement.repository;

import com.example.NewsManagement.model.News;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
    @Override
    @EntityGraph(attributePaths = {"userAccount.roles"})
    @NonNull
    Page<News> findAll(@NonNull Pageable pageable);
}