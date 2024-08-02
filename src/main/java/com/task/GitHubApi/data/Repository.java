package com.task.GitHubApi.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repository {
    String name;
    String ownerLogin;
    List<Branch> branches;
}
