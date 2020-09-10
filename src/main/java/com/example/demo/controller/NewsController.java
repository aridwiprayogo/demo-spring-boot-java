package com.example.demo.controller;

import com.example.demo.domain.News;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("api/news")
public class NewsController {
    final NewsRepository repository;

    @Autowired
    public NewsController(NewsRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/")
    News save(@RequestBody News news) {
        return repository.save(news);
    }

    @GetMapping("/")
    List<News> getAll() {
        return repository.findAll();
    }

    /*@GetMapping("/{id}")
    News getById(@PathVariable("id") String id) throws Throwable {
        final UUID uuid = UUID.fromString(id);
        return repository.findById(uuid).orElseThrow((Supplier<Throwable>) () ->
                new ResourceNotFoundException("News "+ id + "not found"));
    }*/

    @GetMapping("/{author}")
    List<News> getByName_author(@PathVariable("author") String author){
        return repository.findByAuthor(author);
    }

    @PutMapping("/{id}")
    News update(@PathVariable("id") String id,
            @org.jetbrains.annotations.NotNull @RequestBody News newsInput) throws Throwable {
        final News news = repository.findById(UUID.fromString(id)).orElseThrow((Supplier<Throwable>) ()->
                new ResourceNotFoundException("News "+id+" not found"));
        if (newsInput.getAuthor() != null) {
            news.setAuthor(newsInput.getAuthor());
        }
        if(newsInput.getHead() != null){
            news.setHead(newsInput.getHead());
        }
        if (newsInput.getBody()!=null){
            news.setBody(newsInput.getBody());
        }
        return repository.save(news);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") String id){
        final UUID uuid = UUID.fromString(id);
        final Optional<News> news = repository.findById(uuid);
        news.ifPresent(repository::delete);
    }
}
