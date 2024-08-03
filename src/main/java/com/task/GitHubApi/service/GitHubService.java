package com.task.GitHubApi.service;

import com.task.GitHubApi.data.Repository;
import com.task.GitHubApi.data.response.ApiResponse;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class GitHubService {

    final ApiService apiService;

    @NonNull
    public ApiResponse<List<Repository>> getRepositories(String username) {
        try {
            return apiService.fetchRepositories(username);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
