package com.example.githubrepoviewer.domain.use_case


import com.example.githubrepoviewer.common.Resources
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO
import com.example.githubrepoviewer.domain.repo.GitReposListRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

interface IGetGitRepoInfoUseCase {
    fun getGitRepoItem(ownerName: String?, repoName: String?): Flow<Resources<RepoDetailItemDTO>>
}

class GetGitRepoInfoUseCase(
    private val repo: GitReposListRepo
) : IGetGitRepoInfoUseCase {


      override fun getGitRepoItem(ownerName: String?, repoName:String?):Flow<Resources<RepoDetailItemDTO>> = flow {
        emit(Resources.Loading<RepoDetailItemDTO>())

        try {

            val gitRepos = repo.getGitRepoDetails(ownerName,repoName)
            repo.insertGitRepo(gitRepos)
            emit(Resources.Success<RepoDetailItemDTO>(gitRepos))
            println("remote Repo ${gitRepos.name}")


        }catch (e: HttpException){
            emit(Resources.Error<RepoDetailItemDTO>(e.localizedMessage?:"An Unexpected Error."))
        }catch (e:IOException){
           emit(Resources.Error<RepoDetailItemDTO>("Check internetConnection."))
        }

    }


}