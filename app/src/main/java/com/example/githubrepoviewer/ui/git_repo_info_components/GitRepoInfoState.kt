package com.example.githubrepoviewer.ui.git_repo_info_components

import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO

data class GitRepoInfoState(
     val isLoading:Boolean = false,
     val repoDetailItemDTO: RepoDetailItemDTO?=null,
     val error:String = ""
)
