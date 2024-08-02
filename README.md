# GitHubApi-Recruitment-Task

<h2>Description</h2>
This Spring Boot application fetches non-fork repositories for a given GitHub username along with branch names and their last commit SHA.

<h2>API Endpoints</h2>
Returns non-fork repositories for a given GitHub username along with branch names and their last commit SHA <br>
<b>URL</b> /api/repositories/{username} <br>
<b>Method</b> GET

<br><b>Response</b> <br>
<h4><i>200 OK</i></h4>

```
[
    {
        "name": "repository-name",
        "ownerLogin": "owner-login",
        "branches": [
            {
                "name": "branch-name",
                "lastCommitSha": "commit-sha"
            }
        ]
    }
]
```
<h4><i>404 Not found</i></h4>

```
{
    "status": 404,
    "message": "User not found"
}
```
