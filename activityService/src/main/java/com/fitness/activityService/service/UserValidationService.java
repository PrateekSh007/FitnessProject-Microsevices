package com.fitness.activityService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    @Autowired
    private final WebClient userServiceWebClient ;

    public boolean validateUser(Long userId) {
        try {
            return Boolean.TRUE.equals(userServiceWebClient.get()
                    .uri("users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        } catch (WebClientResponseException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("User not found:" + userId) ;
            }
            else if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Invalid Request") ;
            }
        }
        return false;
    }

}
