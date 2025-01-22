package com.sachith.SpringSecEx.controller;

import com.sachith.SpringSecEx.dto.Request;
import com.sachith.SpringSecEx.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("")
    public String greet(HttpServletRequest request,@AuthenticationPrincipal UserDetails userDetails){
//        request.getHeader("Authorization")
        return "Welcome to Spring Security \n session id :"+request.getSession().getId()
                + " \n user name: " +userDetails.getUsername()
                +"\n roles : "+userDetails.getAuthorities();
    }

    @PostMapping("")
    public Response postTest(@RequestBody Request request){
        return new Response("Success");
    }
}
