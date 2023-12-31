package com.example.githubrepoviewer.domain.repo

import androidx.paging.PagingData
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTOItem
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTO
import kotlinx.coroutines.flow.Flow

interface GitReposListRepo {

    suspend fun getGitRepoIssues(owner: String?, repo: String?): GitRepoIssuesDTO

    suspend fun retrieveGitRepoByOwnerName(ownerName: String?): RepoDetailItemDTO

    suspend fun getGitRepos(): Flow<PagingData<GitReposDTOItem>>

    suspend fun getGitRepoDetails(owner: String?, repo: String?): RepoDetailItemDTO

    suspend fun insertGitRepo(repoDetailItemDTO: RepoDetailItemDTO)

    suspend fun retrieveGitRepo(id: Int?): RepoDetailItemDTO

    suspend fun updateGitRepo(repoDetailItemDTO: RepoDetailItemDTO)

    suspend fun deleteGitRepo(id: Int?)

}