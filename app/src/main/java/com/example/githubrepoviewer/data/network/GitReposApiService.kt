package com.example.githubrepoviewer.data.network

import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTO
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitReposApiService {


    @GET("/repositories")
    suspend fun getGitRepos(
        @Query("page") pageNumber:Int
    ): GitReposDTO


    @GET("/repos/{owner}/{repo}")
    suspend fun getGitRepoDetails(
        @Path("owner") owner:String?,
        @Path("repo") repo:String?,
    ): RepoDetailItemDTO



    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getGitRepoIssues(
        @Path("owner") owner:String?,
        @Path("repo") repo:String?,
    ):GitRepoIssuesDTO


}