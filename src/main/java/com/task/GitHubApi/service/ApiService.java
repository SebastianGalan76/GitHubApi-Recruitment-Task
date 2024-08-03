package com.task.GitHubApi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.GitHubApi.config.ApiConfig;
import com.task.GitHubApi.data.Branch;
import com.task.GitHubApi.data.Repository;
import com.task.GitHubApi.data.dto.BranchDto;
import com.task.GitHubApi.data.dto.ErrorDto;
import com.task.GitHubApi.data.dto.RepositoryDto;
import com.task.GitHubApi.data.response.ApiResponse;
import com.task.GitHubApi.data.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApiService {
    final ApiConfig apiConfig;

    final HttpClient client = HttpClient.newHttpClient();
    final ObjectMapper objectMapper = new ObjectMapper();


    public ApiResponse<List<Repository>> fetchRepositories(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiConfig.getRepositoryApiUrl(username)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            List<RepositoryDto> repositoriesDto = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
            List<Repository> repositories = repositoriesDto.stream()
                    .map(dto -> {
                        try {
                            return fetchRepositoryWithBranches(dto);
                        } catch (IOException | InterruptedException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();

            return new ApiResponse<>(repositories, null);
        }
        ErrorDto errorDto = objectMapper.readValue(response.body(), ErrorDto.class);
        return new ApiResponse<>(null, new ErrorResponse(statusCode, errorDto.message()));
    }

    public Repository fetchRepositoryWithBranches(RepositoryDto repositoryDto) throws IOException, InterruptedException {
        List<BranchDto> branchDTOs = fetchBranches(repositoryDto.getApiUrlForBranches(
                apiConfig.getBranchApiUrl(
                        repositoryDto.name(),
                        repositoryDto.owner().login()
                )));

        List<Branch> branches = branchDTOs.stream()
                .map(BranchDto::toBranch)
                .toList();

        return new Repository(
                repositoryDto.name(),
                repositoryDto.owner().login(),
                branches);
    }

    public List<BranchDto> fetchBranches(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } else {
            throw new IOException("Failed to fetch branches: HTTP code " + response.statusCode());
        }
    }
}
