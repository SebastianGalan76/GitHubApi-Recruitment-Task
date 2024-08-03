package com.task.GitHubApi.data.response;

public record ApiResponse<T>(T data, ErrorResponse error) {
}
