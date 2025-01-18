package com.sachith.SpringSecEx.service;

import org.springframework.stereotype.Service;

@Service
public class JWTService {
    public String generateToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6InNhY2hpdGgiLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTUxNjIzOTAyMn0.koGnXjQAfM5Pu3dEJtZdBbr3WBdskxnFj7kXKfG06MM";
    }
}
