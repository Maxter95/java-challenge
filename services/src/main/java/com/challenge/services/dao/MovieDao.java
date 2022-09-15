package com.challenge.services.dao;

import com.challenge.services.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieDao extends JpaRepository<Movies, Long> {
    Movies findBymName(String mName);
}
