package com.example.githubrepoviewer.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTOItem

interface IReposPagingSource {
    fun getRefreshKey(state: PagingState<Int, GitReposDTOItem>): Int?

    suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, GitReposDTOItem>
}