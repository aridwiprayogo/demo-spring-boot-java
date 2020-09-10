package com.example.demo.repository;

import com.example.demo.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID>{

    List<News> findByAuthor(String author);
}
