package com.task.GitHubApi.service;

import com.task.GitHubApi.data.Branch;
import com.task.GitHubApi.data.Repository;
import com.task.GitHubApi.data.exception.UserNotFoundException;
import com.task.GitHubApi.util.ApiResponse;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Service
@RequiredArgsConstructor
public class GitHubService {
    @Value("${github.api}")
    String githubApiUrl;

    @NonNull
    public List<Repository> getRepositories(String username) throws UserNotFoundException {
        String url = githubApiUrl + "/users/" + username + "/repos";

        try {
            List<Map<String, Object>> repositoryList = ApiResponse.getBody(url);
            if (repositoryList == null) {
                return new ArrayList<>();
            }

            List<Repository> repositories = new ArrayList<>();
            for (Map<String, Object> repository : repositoryList) {
                if (!((boolean) repository.get("fork"))) {
                    String repositoryName = (String) repository.get("name");

                    @SuppressWarnings("unchecked")
                    String repositoryOwner = (String) ((Map<String, Object>) repository.get("owner")).get("login");

                    repositories.add(new Repository(repositoryName, repositoryOwner, getBranches(repositoryName, repositoryOwner)));
                }
            }

            return repositories;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException();
            }
        }

        return new ArrayList<>();
    }

    @NonNull
    private List<Branch> getBranches(String repositoryName, String repositoryOwner) {
        String url = githubApiUrl + "/repos/" + repositoryOwner + "/" + repositoryName + "/branches";

        List<Map<String, Object>> branchList = ApiResponse.getBody(url);

        List<Branch> branches = new ArrayList<>();
        if (branchList != null) {
            for (Map<String, Object> branch : branchList) {
                String branchName = (String) branch.get("name");

                @SuppressWarnings("unchecked")
                String lastCommitSha = (String) ((Map<String, Object>) branch.get("commit")).get("sha");

                branches.add(new Branch(branchName, lastCommitSha));
            }
        }
        return branches;
    }
}
