package com.task.GitHubApi.service;

import com.task.GitHubApi.data.Repository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class GitHubService {
    public List<Repository> getRepositories(String username) {

    }
}
