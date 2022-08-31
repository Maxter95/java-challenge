package com.challenge.services.controller;

import com.challenge.services.dao.RoleDao;
import com.challenge.services.dao.UserDao;
import com.challenge.services.entity.Role;
import com.challenge.services.entity.RoleName;
import com.challenge.services.entity.User;
import com.challenge.services.exception.AppException;
import com.challenge.services.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.challenge.services.payload.ApiResponse;
import com.challenge.services.payload.LoginRequest;
import com.challenge.services.payload.SignUpRequest;
import com.challenge.services.security.JwtTokenProvider;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userRepository;

    @Autowired
    RoleDao roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        HashMap<String, Object> hmap = new HashMap<String, Object>();
        hmap.put("authority", authorities.toString());
        hmap.put("accessToken", jwt);
        return ResponseEntity.ok(hmap.get("accessToken"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username already used!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email already used!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User( signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(new Date());
        user.setUpdated(new Date());
        Role userRole ;
        if (signUpRequest.getRole().equals("ROLE_USER")) {
        	    userRole = roleRepository.findByName(RoleName.ROLE_USER)
                       .orElseThrow(() -> new AppException("User Role not set."));
        }else {
        	 userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException("User Role not set."));
        }
        
        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "You are successfully registered"));
    }
    //@RequestMapping(path="/UpgradeUser",method= {RequestMethod.PUT})
    @PutMapping(path="/UpgradeUser")
    public String upgradePermission (@RequestParam String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Collection<? extends GrantedAuthority> currentUserName = authentication.getAuthorities();
            GrantedAuthority currentUserRole = currentUserName.iterator().next();
            User userToUpgrade = userRepository.findByUsername(username);
            if (!(currentUserRole.toString()).equals("ROLE_ADMIN")) {
                return "you don't have to permession to upgrade a user";
            }else {



                Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                        .orElseThrow(() -> new AppException("User Role not set."));
                userToUpgrade.getRoles().clear();
                userToUpgrade.getRoles().add(userRole);
                userRepository.save(userToUpgrade);
                System.out.println(userToUpgrade.getRoles());
            }
        }

        return username;
    }

}