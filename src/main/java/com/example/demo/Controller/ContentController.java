package com.example.demo.Controller;
import java.net.ResponseCache;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Model.Content;

import com.example.demo.Repository.ContentCollectionrepository;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/content")
public class ContentController {
    
    private final ContentCollectionrepository repository;
   
    public ContentController(ContentCollectionrepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")    
    public List<Content>FindAll(){
        return repository.findAll();
    }

    @GetMapping("/getContentbyid/{id}")
    public Optional<Content> getContentByid(@PathVariable("id") Integer id){
        return Optional.ofNullable(repository.FindById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found with id: " + id)));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createContent")        
    public void Create(@RequestBody Content content){
        repository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)    
    @PutMapping("/Update/{id}")
public void update(@RequestBody Content content, @PathVariable Integer id) {

    if (!repository.existById(id)) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found with id: " + id);
    }

    repository.Update(content, id);
}

@ResponseStatus(HttpStatus.NO_CONTENT)
@DeleteMapping("/delete/{id}")
public void delete(@PathVariable Integer id) {
    if (!repository.existById(id)) {

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found with id: " + id);

    }
        repository.deleteById(id);
   

    

}

}
