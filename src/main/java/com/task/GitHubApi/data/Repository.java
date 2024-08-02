package com.task.GitHubApi.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repository {
    String name;
    String ownerLogin;
    List<Branch> branches;
}
