package com.sachith.SpringSecEx.controller;

import com.sachith.SpringSecEx.dto.Request;
import com.sachith.SpringSecEx.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("")
    public String greet(HttpServletRequest request){
        return "Welcome to Spring Security "+request.getSession().getId();
    }

    @PostMapping("")
    public Response postTest(@RequestBody Request request){
        return new Response("Success");
    }
}
