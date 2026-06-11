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


    private final List<Content> content = new ArrayList<>();
    public ContentCollectionrepository(){

    }

    public List<Content> findAll(){
        return content;
    }

    public Optional<Content>FindById(Integer id){
        return content.stream().filter(c -> c.id().equals(id)).findFirst();
    }



    @PostConstruct
    private void init()
    {
        Content c=new Content(1,"Java Basics","Learn the basics of Java programming",enStatus.Idea,enType.Article,LocalDateTime.now(),LocalDateTime.now(),"https://example.com/java-basics");
         content.add(c);
    }

    public void create(Content newContent){
        this.content.add(newContent);
    }


}
