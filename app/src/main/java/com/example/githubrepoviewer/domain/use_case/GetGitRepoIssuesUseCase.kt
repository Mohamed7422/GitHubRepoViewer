package com.example.githubrepoviewer.domain.use_case


import com.example.githubrepoviewer.common.Resources
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTO
import com.example.githubrepoviewer.domain.repo.GitReposListRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

interface IGetGitRepoIssuesUseCase {
    operator fun invoke(ownerName: String?, repoName: String?): Flow<Resources<GitRepoIssuesDTO>>
}

class GetGitRepoIssuesUseCase(
    private val repo: GitReposListRepo
) : IGetGitRepoIssuesUseCase {

   override operator fun invoke(ownerName: String?, repoName: String?):Flow<Resources<GitRepoIssuesDTO>> = flow {
        emit(Resources.Loading<GitRepoIssuesDTO>())

        try {
            val gitRepoIssues = repo.getGitRepoIssues(ownerName, repoName)
            emit(Resources.Success<GitRepoIssuesDTO>(gitRepoIssues))
            println("From Use Case $gitRepoIssues")


        }catch (e: HttpException){
            emit(Resources.Error<GitRepoIssuesDTO>(e.localizedMessage?:"An Unexpected Error."))

        }catch (e:IOException){
            emit(Resources.Error<GitRepoIssuesDTO>(e.localizedMessage?:"Check internetConnection."))

        }
    }
}