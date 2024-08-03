package com.task.GitHubApi.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.task.GitHubApi.data.Branch;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchDto(String name, Commit commit) {
    public Branch toBranch(){
        return new Branch(name, commit.sha());
    }

    public record Commit(String sha) {

    }
}
