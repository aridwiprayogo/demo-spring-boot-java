package com.example.demo.repository;

import com.example.demo.CustomPostgresqlContainer;
import com.example.demo.domain.News;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class NewsRepositoryTest extends CustomPostgresqlContainer{
    @Autowired
    private NewsRepository repository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAll(){
        final List<News> newsList = this.repository.findAll();
        assertThat(newsList).hasSize(6);
        assertThat(newsList.get(1)).isNotNull();
        final String author = newsList.get(1).getAuthor();
        assertThat(author).isEqualTo("ari dwi prayogo");
    }

    @Test
    public void findOneShouldReturnMatchingEntityIfAvailable() {
        News news = new News("ari dwi prayogo",
                "belajar testcontainer",
                "ternyata asik bisa unit testing");

        entityManager.persistAndFlush(news);
        News found = repository.findById(news.getId()).orElseThrow();

        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found).isEqualTo(news);
    }
}