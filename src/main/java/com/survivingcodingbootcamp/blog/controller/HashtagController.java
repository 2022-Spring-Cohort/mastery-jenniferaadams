package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.repository.HashtagRepository;
import com.survivingcodingbootcamp.blog.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HashtagController {
    private HashtagRepository hashtagRepo;
    private PostRepository postRepo;

    public HashtagController(HashtagRepository hashtagRepo, PostRepository postRepo) {

        this.hashtagRepo = hashtagRepo;
        this.postRepo = postRepo;
    }

    @RequestMapping("/hashtags")
    public String showAllHashtagsTemplate(Model model) {
        model.addAttribute("hashtags", hashtagRepo.findAll());
        return "all-hashtags-template";
    }
    @RequestMapping("/hashtags/{hashtagId}")
    public String showSingleHashtagTemplate(Model model, @PathVariable long hashtagId) {
        model.addAttribute("hashtag", hashtagRepo.findById(hashtagId).get());
        model.addAttribute("hashtagName", hashtagRepo.findById(hashtagId).get().getHashtag());
        return "single-hashtag-template";
    }
    @PostMapping("/SubmitHashtag")
    public String addHashtag(@RequestParam String hashtag, @RequestParam Long id) {
        Post thePost = postRepo.findById(id).get();

        Optional<Hashtag> tempHashtag = hashtagRepo.findByHashtagIgnoreCase(hashtag);
        if(tempHashtag.isPresent()){
            if(!tempHashtag.get().containsPost(thePost)){
                tempHashtag.get().addPost(thePost);
                hashtagRepo.save(tempHashtag.get());
            }
        }
        else {
            Hashtag theHashtag = new Hashtag(hashtag, thePost);
            hashtagRepo.save(theHashtag);
        }
        return "redirect:/hashtags";
    }
}


