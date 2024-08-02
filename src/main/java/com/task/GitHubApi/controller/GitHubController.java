package com.task.GitHubApi.controller;

import com.task.GitHubApi.data.exception.UserNotFoundException;
import com.task.GitHubApi.data.response.ErrorResponse;
import com.task.GitHubApi.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GitHubController {
    final GitHubService service;

    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getUserRepositories(@PathVariable String username) {
        try {
            return ResponseEntity.ok(service.getRepositories(username));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, e.getMessage()));
        }
    }
}
