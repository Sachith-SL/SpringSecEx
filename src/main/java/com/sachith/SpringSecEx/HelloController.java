package com.sachith.SpringSecEx;

import com.sachith.SpringSecEx.dto.Request;
import com.sachith.SpringSecEx.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String greet(HttpServletRequest request){
        return "Welcome to Spring Security "+request.getSession().getId();
    }

    @PostMapping("/")
    public Response postTest(@RequestBody Request request){
        return new Response("Success");
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){

        return (CsrfToken) request.getAttribute("_csrf");
    }
}
