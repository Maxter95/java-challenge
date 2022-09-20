package com.challenge.services.controller;

import com.challenge.services.ServicesApplication;
import com.challenge.services.dao.MovieDao;
import com.challenge.services.dao.UserDao;
import com.challenge.services.entity.Movies;
import com.challenge.services.entity.User;
import com.challenge.services.payload.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServicesApplication.class)
@WebAppConfiguration
public class UserControllerTest {
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    MovieDao movieDao;
    @Autowired
    UserDao userDao;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    @Order(3)
    public void AddMovieToFavorite() throws Exception {
        String uri = "/api/user/movie-to-add";
        Movies mv = new Movies();
        mv.setId(999L);
        mv.setmName("XP");
        mv.setStars(1);
        User u = userDao.findByUsername("max3");
        u.getMoviesList().add(mv);
        String inputJson = mapToJson(u);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertTrue(u.getMoviesList().contains(mv));
    }
    @Test
    @Order(4)
    public void RemoveMovieFromFavorite() throws Exception {
        String uri = "/api/user/movie-to-remove";
        Movies mv = new Movies();
        mv.setId(999L);
        mv.setmName("XP");
        mv.setStars(1);
        User u = userDao.findByUsername("max3");
        u.getMoviesList().remove(mv);
        String inputJson = mapToJson(u);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertFalse(u.getMoviesList().contains(mv));
    }
    @Test
    @Order(1)
    public void login()throws Exception{
        String uri = "/api/auth/signin";
        LoginRequest login=new LoginRequest();
        login.setUsernameOrEmail("max");
        login.setPassword("12345678");
        String inputJson = mapToJson(login);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
   /* @Test
    @Order(2)
    public void getFavoriteMovie() throws Exception {
        String uri = "/api/user/favorite-movies";




        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Movies[] movieList = mapFromJson(content, Movies[].class);
        assertTrue(movieList.length > 0);
    }*/
}

