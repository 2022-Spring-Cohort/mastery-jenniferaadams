package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import com.survivingcodingbootcamp.blog.repository.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostRepository postRepo;
    private TopicRepository topicRepo;

    public PostController(PostRepository postRepo, TopicRepository topicRepo) {
        this.postRepo = postRepo;
        this.topicRepo = topicRepo;
    }

    @GetMapping("/{id}")
    public String displaySinglePost(@PathVariable long id, Model model) {
        model.addAttribute("post", postRepo.findById(id).get());
        return "single-post-template";
    }

    @PostMapping("/SubmitPost/{id}")
    public String showAddPost(@PathVariable long id, @RequestParam String title, @RequestParam String author, @RequestParam String content) {

        Topic theTopic = topicRepo.findById(id).get();
        Post thePost = new Post(title, theTopic, content, author);
        postRepo.save(thePost);
        return "redirect:/topics/"+id;

    }
}