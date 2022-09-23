package com.challenge.services.service;


import com.challenge.services.dao.MovieDao;
import com.challenge.services.entity.Movies;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MovieService {
    @Autowired
    MovieDao movieDao;

    @RateLimiter(name="processService")
    public Page<Movies> top10Movies(){
        return movieDao.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("stars"))));

    }
    @CircuitBreaker(name = "myCircuitBreaker",fallbackMethod = "fetchMoviesFailOver")
    public  String fetchMovies(){

        return  WebClient.create()
                .get()
                .uri("https://imdb-api.com/en/API/MostPopularMovies/k_8hts3c8y")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }
    public  String fetchMoviesFailOver(){

        return  WebClient.create()
                .get()
                .uri("https://imdb-api.com/en/API/MostPopularMovies/k_12345678")
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }

}
