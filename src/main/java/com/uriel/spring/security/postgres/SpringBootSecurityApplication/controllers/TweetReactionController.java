package com.uriel.spring.security.postgres.SpringBootSecurityApplication.controllers;

// imports
import jakarta.validation.Valid;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.Tweeter;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.TweeterReaction;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.User;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.payload.request.TweetReactionRequest;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.Reaction;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.repository.ReactionRepository;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.repository.UserRepository;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.repository.TweetRepository;
import com.uriel.spring.security.postgres.SpringBootSecurityApplication.repository.TweetReactionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reactions")

public class TweetReactionController {
    @Autowired
    private TweetReactionRepository tweetReactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private ReactionRepository reactionRepository;

    @GetMapping("/all")
    public Page<TweeterReaction> getTweet(Pageable pageable) {
        return tweetReactionRepository.findAll(pageable);
    }

    @PostMapping("/create")
    public TweeterReaction createReaction(@Valid @RequestBody TweetReactionRequest tweetReaction) {
        System.out.println("tweetid : " + tweetReaction.getTweetId());
        System.out.println("reactiontid : " + tweetReaction.getReactionId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println("userid : " + userId);

        User user = getValidUser(userId);
        System.out.println("user");

        System.out.println(user);

        TweeterReaction myTweetReaction = new TweeterReaction();
        Tweeter myTweet = getValidTweet(tweetReaction.getTweetId());
        System.out.println("object tweet : ");
        System.out.println(myTweet);

        Reaction myReaction = getValidReaction(tweetReaction.getReactionId());
        System.out.println("object reaction : ");
        System.out.println(myReaction);

        // myTweetReaction.setUserId(user.getId());
        // myTweetReaction.setTweetId(myTweet.getId());
        // myTweetReaction.setReactionId(myReaction.getId());

        myTweetReaction.setUser(user);
        myTweetReaction.setTweet(myTweet);
        myTweetReaction.setReaction(myReaction);

        System.out.println("tweet reaction : ");
        System.out.println(myTweetReaction.getReactionId());
        System.out.println(myTweetReaction.getTweetId());

        System.out.println(myTweetReaction.getUserId());

        tweetReactionRepository.save(myTweetReaction);

        return myTweetReaction;
    }

    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }

    private Tweeter getValidTweet(Long tweetId) {
        Optional<Tweeter> tweetOpt = tweetRepository.findById(tweetId);
        if (!tweetOpt.isPresent()) {
            throw new RuntimeException("Tweet not found");
        }
        return tweetOpt.get();
    }

    private Reaction getValidReaction(Long reactionId) {
        Optional<Reaction> reactionOpt = reactionRepository.findById(reactionId);
        if (!reactionOpt.isPresent()) {
            throw new RuntimeException("Reaction not found");
        }
        return reactionOpt.get();
    }

}
