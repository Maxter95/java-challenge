package com.challenge.services.controller;

import com.challenge.services.dao.UserDao;
import com.challenge.services.entity.Role;
import com.challenge.services.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    private UserDao userRepository;
    @Test
    @Order(1)
    @Rollback(value = false)
    void saveUser() {
        User user = User.builder().
                username("max")
                .email("max@max.x")
                .password("1234567")
               .build();
        userRepository.save(user);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getUserTest(){

        User user = userRepository.findById(1L).get();

        Assertions.assertThat(user.getId()).isEqualTo(1L);

    }
    public void getListOfUsersTest(){

        List<User> users = userRepository.findAll();

        Assertions.assertThat(users.size()).isGreaterThan(0);

    }
    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest(){

        User user = userRepository.findById(1L).get();

        user.setEmail("ram@gmail.com");

        User userUpdated =  userRepository.save(user);

        Assertions.assertThat(userUpdated.getEmail()).isEqualTo("ram@gmail.com");

    }


}