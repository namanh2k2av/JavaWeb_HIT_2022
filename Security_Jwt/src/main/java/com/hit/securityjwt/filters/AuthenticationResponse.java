package com.hit.securityjwt.filters;

public class AuthenticationResponse {
    private String jwt;
    private Integer id;
    private String username;

    public AuthenticationResponse(String jwt, Integer id, String username) {
    }
}
