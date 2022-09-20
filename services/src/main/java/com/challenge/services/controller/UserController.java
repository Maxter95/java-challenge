package com.challenge.services.controller;


import com.challenge.services.dao.MovieDao;
import com.challenge.services.dao.UserDao;
import com.challenge.services.entity.Movies;
import com.challenge.services.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    MovieDao movieRepository;
    @Autowired
    UserDao userRepository;

    @Transactional
    @PutMapping(path = "/movie-to-add")
    public void addToFavorite(@RequestParam String movieName) {

        Movies mv = movieRepository.findBymName(movieName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User u = userRepository.findByUsername(currentUserName);
            mv.setStars(mv.getStars() + 1);
            u.getMoviesList().add(mv);

            userRepository.save(u);
            movieRepository.save(mv);
        }


    }
    @Transactional
    @PutMapping(path = "/movie-to-remove")
    public void removeFromFavorite(@RequestParam String movieName) {

        Movies mv = movieRepository.findBymName(movieName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User u = userRepository.findByUsername(currentUserName);
            if (mv.getStars() > 0) {
                mv.setStars(mv.getStars() - 1);
            }
            u.getMoviesList().remove(mv);
            userRepository.save(u);
            movieRepository.save(mv);
        }

    }

    @GetMapping(path = "/favorite-movies")
    public Set<Movies> getFavoriteMvList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            User u = userRepository.findByUsername(currentUserName);
            return u.getMoviesList();

    }

}
