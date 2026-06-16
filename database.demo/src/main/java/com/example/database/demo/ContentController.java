package com.example.database.demo;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.example.database.demo.repository.ContentRepository;
@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentRepository repository;

    public ContentController(ContentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<Content> all() {
        return repository.findAll();
    }

    @PostMapping("/create")
    public Content create(@RequestBody Content content) {
        return repository.save(content);
    }

    @PutMapping("/{id}")
    public Content update(@PathVariable Integer id, @RequestBody Content content) {
        content.setId(id);
        return repository.save(content);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}