package com.uriel.spring.security.postgres.SpringBootSecurityApplication.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.TweeterReaction;
@Repository
public interface TweetReactionRepository extends JpaRepository<TweeterReaction, Long>{

} 
