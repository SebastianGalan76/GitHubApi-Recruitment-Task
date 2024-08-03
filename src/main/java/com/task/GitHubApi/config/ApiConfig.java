package com.task.GitHubApi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig {
    @Value("${github.api}")
    private String githubApiUrl;

    @Value("${github.api.repository}")
    private String repositoryEndpoint;

    @Value("${github.api.branch}")
    private String branchEndpoint;

    public String getRepositoryApiUrl(String username){
        return repositoryEndpoint.formatted(githubApiUrl, username);
    }

    public String getBranchApiUrl(String repositoryName, String username){
        return branchEndpoint.formatted(githubApiUrl, username, repositoryName);
    }
}
