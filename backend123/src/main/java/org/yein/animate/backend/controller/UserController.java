package org.yein.animate.backend.controller;

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yein.animate.backend.entity.User;
import org.yein.animate.backend.repository.UserRepository;
import org.yein.animate.backend.service.JwtService;
import org.yein.animate.backend.serviceImpl.JwtServiceImpl;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/animate/user/login")
    public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse response){
        User user = userRepository.findByUserIdAndUserPassword(params.get("userId"), params.get("userPassword"));
        if(user != null){

            int userNo =  user.getUserNo();
            String token = jwtService.getToken("userNo", userNo);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            return new ResponseEntity<>(userNo, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/animate/user/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false)String token){
        Claims claims =jwtService.getClaims(token);
        if(claims != null){
            int userNo = Integer.parseInt(claims.get("userNo").toString());
            return new ResponseEntity<> (userNo,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
