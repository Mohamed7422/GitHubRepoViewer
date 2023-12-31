package com.example.githubrepoviewer.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubrepoviewer.data.network.GitReposApiService
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTOItem
import retrofit2.HttpException
import java.io.IOException

class ReposPagingSource(private val api: GitReposApiService):PagingSource<Int,GitReposDTOItem>(),
    IReposPagingSource {
    override fun getRefreshKey(state: PagingState<Int, GitReposDTOItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitReposDTOItem> {

        return try {
            val currentPage = params.key?:1
            val gitRepos = api.getGitRepos(pageNumber = currentPage)
            LoadResult.Page(data = gitRepos,
                prevKey = if (currentPage==1)null else currentPage-1,
                nextKey = if (gitRepos.isEmpty())null else currentPage+ 1)

        }catch (e:HttpException){
            LoadResult.Error(e)
        }catch (e:IOException){
            LoadResult.Error(e)
        }
    }
}