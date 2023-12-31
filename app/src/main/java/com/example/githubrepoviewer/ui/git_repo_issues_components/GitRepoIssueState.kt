package com.example.githubrepoviewer.ui.git_repo_issues_components

import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTO

data class GitRepoIssueState(
    val isLoading:Boolean = false,
    val repoIssue:GitRepoIssuesDTO = GitRepoIssuesDTO(),
    var error:String = ""
)
