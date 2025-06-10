package com.uriel.spring.security.postgres.SpringBootSecurityApplication.controllers;

import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.Tweeter;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.repository.TweetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.User;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tweets")
public class TweeterController {

  @Autowired
  private TweetRepository tweetRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/all")
  public Page<Tweeter> getTweet(Pageable pageable) {
    return tweetRepository.findAll(pageable);
  }

  @PostMapping("/create")
  public Tweeter createTweet(@Valid @RequestBody Tweeter tweet) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userId = authentication.getName();
    System.out.println("userid : " + userId);

    User user = getValidUser(userId);
    System.out.println("user");

    System.out.println(user);
    Tweeter myTweet = new Tweeter(tweet.getTweet());
    myTweet.setPostedBy(user);
    tweetRepository.save(myTweet);

    return myTweet;
  }

  private User getValidUser(String userId) {
    Optional<User> userOpt = userRepository.findByUsername(userId);
    if (!userOpt.isPresent()) {
      throw new RuntimeException("User not found");
    }
    return userOpt.get();
  }

}
