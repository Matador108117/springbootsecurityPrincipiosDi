package com.uriel.spring.security.postgres.SpringBootSecurityApplication.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uriel.spring.security.postgres.SpringBootSecurityApplication.models.Tweeter;
@Repository
public interface TweetRepository extends JpaRepository<Tweeter, Long> {

}
