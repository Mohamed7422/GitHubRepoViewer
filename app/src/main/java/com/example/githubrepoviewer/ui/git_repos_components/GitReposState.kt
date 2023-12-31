package com.example.githubrepoviewer.ui.git_repos_components

import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTO

data class GitReposState(
    val gitRepos: GitReposDTO = GitReposDTO(),
    val error:String = "",
    val isLoading : Boolean = false
)
