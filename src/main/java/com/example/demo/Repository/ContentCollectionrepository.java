package com.example.demo.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.Model.Content;
import com.example.demo.Model.enStatus;
import com.example.demo.Model.enType;

import jakarta.annotation.PostConstruct;

@Repository
public class ContentCollectionrepository {

    private final List<Content> contentList = new ArrayList<>();

    public List<Content> findAll() {
        return contentList;
    }

    public Optional<Content> FindById(Integer id) {
        return contentList.stream()
                .filter(c -> c.id().equals(id))
                .findFirst();
    }

    public void save(Content content) {
        contentList.removeIf(c->c.id().equals(content.id()));
        contentList.add(content);
    }

    public boolean existById(Integer id) {
        return contentList.stream()
                .anyMatch(c -> c.id().equals(id));
    }

    public void Update(Content content, Integer id) {
        for (int i = 0; i < contentList.size(); i++) {
            if (contentList.get(i).id().equals(id)) {
                contentList.set(i, content);
                return;
            }
        }
    }

    @PostConstruct
    private void init() {
        Content c = new Content(
                1,
                "Java Basics",
                "Learn the basics of Java programming",
                enStatus.Idea,
                enType.Article,
                LocalDateTime.now(),
                LocalDateTime.now(),
                "https://example.com/java-basics"
        );

        contentList.add(c);
    }

    public void deleteById(Integer id) {
   
    contentList.removeIf(c->c.id().equals(id));


   
   
    }
}