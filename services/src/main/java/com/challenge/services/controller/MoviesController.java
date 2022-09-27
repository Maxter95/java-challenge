package com.challenge.services.controller;


import com.challenge.services.dao.MovieDao;
import com.challenge.services.entity.Movies;
import com.challenge.services.service.MovieService;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    @Autowired
    MovieDao MovieRepository;
    @Autowired
    MovieService movieService;

    @PostMapping(path="/movies-list")
    public void setMovies() throws IllegalArgumentException, JSONException {

        String json = movieService.fetchMovies();
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("items");
        for (int i = 0; i < arr.length(); i++)
        {
            Movies movie= new Movies();
            movie.setStars(0);
            movie.setmName(arr.getJSONObject(i).getString("title"));
            MovieRepository.save(movie);
        }

    }

    @GetMapping(path="/top-movie")
    public   List<Movies> getTopMovies() {
        List<Movies> top10Movies = new ArrayList<Movies>() {
        };
        Page<Movies> moviesList = movieService.top10Movies();

        for (Movies m : moviesList){
            top10Movies.add(m);
        }

        return top10Movies;
    }
    @GetMapping(path="/all-movies")
    public ArrayList<Movies> getAllMovies() {

        ArrayList<Movies> moviesList = (ArrayList<Movies>) MovieRepository.findAll();


        return moviesList;
    }

}
