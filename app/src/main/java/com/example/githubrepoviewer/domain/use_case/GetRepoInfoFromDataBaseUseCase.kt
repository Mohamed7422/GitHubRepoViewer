package com.example.githubrepoviewer.domain.use_case


import com.example.githubrepoviewer.common.Resources
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO
import com.example.githubrepoviewer.domain.repo.GitReposListRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IGetRepoInfoFromDataBaseUseCase {
    fun getGitRepoItemFromDataBase(repoId: Int?): Flow<Resources<RepoDetailItemDTO>>
}

class GetRepoInfoFromDataBaseUseCase(
    private val repo: GitReposListRepo
) : IGetRepoInfoFromDataBaseUseCase {



      override fun getGitRepoItemFromDataBase(repoId: Int? ):Flow<Resources<RepoDetailItemDTO>> = flow {
        emit(Resources.Loading<RepoDetailItemDTO>())

        try {

             val cachedRepo = repo.retrieveGitRepo(repoId)
            if (cachedRepo != null) {
                emit(Resources.Success<RepoDetailItemDTO>(cachedRepo))
            } else {
                emit(Resources.Error("Repository not found."))
            }

        }catch (e:Exception){
           emit(Resources.Error<RepoDetailItemDTO>("Check ${e.message}."))
        }

    }


}