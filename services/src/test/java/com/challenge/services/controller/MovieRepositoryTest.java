package com.challenge.services.controller;

import com.challenge.services.dao.MovieDao;
import com.challenge.services.dao.UserDao;
import com.challenge.services.entity.Movies;
import com.challenge.services.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieRepositoryTest {

    @Autowired
    private MovieDao movieRepository;
    @Test
    @Order(1)
    @Rollback(value = false)
    void saveMovie() {
        Movies movie = Movies.builder()
                .mName("Beast")
                .stars(0)
               .build();
        movieRepository.save(movie);
        Assertions.assertThat(movie.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getMovieTest(){

        Movies movie = movieRepository.findById(1L).get();

        Assertions.assertThat(movie.getId()).isEqualTo(1L);

    }
    @Test
    @Order(3)
    public void getListOfUsersTest(){

        List<Movies> movies = movieRepository.findAll();

        Assertions.assertThat(movies.size()).isGreaterThan(0);

    }
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest(){

        Movies movie = movieRepository.findById(1L).get();

        movie.setmName("Prey");

        Movies movieUpdated =  movieRepository.save(movie);

        Assertions.assertThat(movieUpdated.getmName()).isEqualTo("Prey");

    }


}