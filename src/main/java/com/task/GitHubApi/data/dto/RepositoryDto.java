package com.task.GitHubApi.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepositoryDto(String name, Owner owner) {

    public String getApiUrlForBranches(String url) {
        return url.formatted(url, owner.login, name);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Owner(String login) {
    }
}
