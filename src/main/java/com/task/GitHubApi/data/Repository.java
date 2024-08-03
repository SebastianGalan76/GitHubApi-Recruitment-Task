package com.task.GitHubApi.data;

import java.util.List;

public record Repository(String name, String ownerLogin, List<Branch> branches) {

}
