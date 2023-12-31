package com.example.githubrepoviewer.domain.use_case


import androidx.paging.PagingData
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTOItem
import com.example.githubrepoviewer.domain.repo.GitReposListRepo
import kotlinx.coroutines.flow.Flow

class GetGitReposListUseCase(
    private val repo: GitReposListRepo
) {

    suspend fun execute():Flow<PagingData<GitReposDTOItem>>{
        return repo.getGitRepos()
    }

//    operator fun invoke():Flow<Resources<GitReposDTO>> = flow {
//        emit(Resources.Loading<GitReposDTO>())
//        try {
//            val gitRepos = repo.getGitRepos()
//            emit(Resources.Success<GitReposDTO>(gitRepos))
//            println("repoOwner from use case $gitRepos")
//
//        }catch (e: HttpException){
//            emit(Resources.Error<GitReposDTO>(e.localizedMessage?:"An Unexpected Error."))
//            println("repoOwner from use case ${e.cause}")
//        }catch (e:IOException){
//           emit(Resources.Error<GitReposDTO>("Check internetConnection."))
//        }
//
//    }



}