package com.example.demo.repository;

import com.example.demo.domain.News;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = {UserRepositoryTCIntegrationTest.Initializer.class})
class NewsRepositoryTest {
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
    public void findOneShouldReturnMatchingEntityIfAvailable() throws Exception {
        News news = new News("ari dwi prayogo",
                "belajar testcontainer",
                "ternyata asik bisa unit testing");

        entityManager.persistAndFlush(news);
        News found = repository.findById(news.getId()).orElseThrow();

        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found).isEqualTo(news);
    }
}