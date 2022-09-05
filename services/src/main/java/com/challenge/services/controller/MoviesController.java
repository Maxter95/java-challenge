package com.challenge.services.controller;


import com.challenge.services.dao.MovieDao;
import com.challenge.services.entity.Movies;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class MoviesController {

    @Autowired
    MovieDao MovieRepository;
    @RequestMapping(path="/moviesList",method= {org.springframework.web.bind.annotation.RequestMethod.POST})
    public void setMovies() throws IllegalArgumentException, IllegalAccessException, JSONException {
       List<Object> itemM=
               Collections.singletonList(RestAssured
                       .get("https://imdb-api.com/en/API/MostPopularMovies/k_8hts3c8y")
                       .as(Object.class));

            Map<String,Object> ss=(Map<String, Object>)itemM.get(0);

      List l = convertObjectToList(ss.values());

        List<Object> object = (List<Object>) l.get(0);
        for(int i=0;i<=object.size();i++) {
            Map<String,Object> m=(Map<String,Object>)object.get(i);
            //System.out.println(m.get("title"));
            Movies movie= new Movies();
            movie.setmName(m.get("title").toString());
            movie.setStars(0);
            MovieRepository.save(movie);

            }




    }
    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }
    @RequestMapping(path="/TopMovie",method= {RequestMethod.GET})
    public TreeMap<Integer, String> getTopMovies() {
       int x=0;
        TreeMap<Integer, String> topMovies = new TreeMap<Integer, String>(Collections.reverseOrder());
        TreeMap<Integer, String> top10Movies = new TreeMap<Integer, String>();
        ArrayList<Movies> moviesList = (ArrayList<Movies>) MovieRepository.findAll();

        for (Movies m : moviesList){
            topMovies.put(m.getStars(),m.getmName());
        }
        if(x<10) {
            for (Map.Entry<Integer, String> entry : topMovies.entrySet()) {
                top10Movies.put(entry.getKey(), entry.getValue());
                x++;
            }
        }
        return topMovies;
    }
    @GetMapping(path="/allMovies")
    public ArrayList<Movies> getAllMovies() {

        ArrayList<Movies> moviesList = (ArrayList<Movies>) MovieRepository.findAll();


        return moviesList;
    }

}
