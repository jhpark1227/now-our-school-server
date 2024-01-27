package com.example.school.auth.service;


public interface AuthCommandService {
    Boolean withdrawUser(String accessToken);
    void logout(String accessToken);


}
