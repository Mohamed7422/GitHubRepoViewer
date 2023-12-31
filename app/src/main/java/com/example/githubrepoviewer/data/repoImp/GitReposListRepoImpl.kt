package com.example.githubrepoviewer.data.repoImp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubrepoviewer.data.localdatabase.GitRepoDao
import com.example.githubrepoviewer.data.network.GitReposApiService
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTOItem
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTO
import com.example.githubrepoviewer.data.pagination.ReposPagingSource
import com.example.githubrepoviewer.domain.repo.GitReposListRepo
import kotlinx.coroutines.flow.Flow

class GitReposListRepoImpl(
    private val api: GitReposApiService,
    private val dao: GitRepoDao
) : GitReposListRepo {

    companion object {
        var instance:GitReposListRepoImpl?=null
        fun getInstance(
            apiService: GitReposApiService,
            dao: GitRepoDao
        ):GitReposListRepoImpl{
            return instance?: synchronized(this){
                val temp = GitReposListRepoImpl(apiService,dao)
                instance = temp
                temp
            }
        }
    }

    override suspend fun getGitRepoIssues(owner: String?, repo: String?): GitRepoIssuesDTO {
        return api.getGitRepoIssues(owner, repo)
    }

    override suspend fun retrieveGitRepoByOwnerName(ownerName: String?): RepoDetailItemDTO {
        return dao.retrieveGitRepoByOwnerName(ownerName)
    }


    override suspend fun getGitRepos(): Flow<PagingData<GitReposDTOItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = {
                ReposPagingSource(api)
            }
        ).flow
    }

    override suspend fun getGitRepoDetails(owner: String?, repo: String?): RepoDetailItemDTO {
        return api.getGitRepoDetails(owner,repo)
    }

    override suspend fun insertGitRepo(repoDetailItemDTO: RepoDetailItemDTO) {
        dao.insertGitRepo(repoDetailItemDTO)
    }

    override suspend fun retrieveGitRepo(id: Int?): RepoDetailItemDTO {
       return dao.retrieveGitRepo(id)
    }

    override suspend fun updateGitRepo(repoDetailItemDTO: RepoDetailItemDTO) {
        dao.updateGitRepo(repoDetailItemDTO)
    }

    override suspend fun deleteGitRepo(id: Int?) {
        dao.deleteGitRepo(id)
    }
}