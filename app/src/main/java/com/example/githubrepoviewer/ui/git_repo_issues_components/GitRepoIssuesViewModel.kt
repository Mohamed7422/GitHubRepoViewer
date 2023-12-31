package com.example.githubrepoviewer.ui.git_repo_issues_components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubrepoviewer.common.Constants
import com.example.githubrepoviewer.common.Resources
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTO
import com.example.githubrepoviewer.domain.use_case.GetGitRepoIssuesUseCase
import com.example.githubrepoviewer.domain.use_case.IGetGitRepoIssuesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GitRepoIssuesViewModel(
    private val getGitRepoIssuesUseCase: IGetGitRepoIssuesUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel() {
       private var _gitRepoIssuesState = mutableStateOf(GitRepoIssueState())
       val gitRepoIssueState : State<GitRepoIssueState> = _gitRepoIssuesState


    init {
        val repoOwner = savedStateHandle.get<String>(Constants.GIT_REPO_OWNER)
        val repoName  = savedStateHandle.get<String>(Constants.GIT_REPO_NAME)
        println("repoOwner and name from ViewModel $repoOwner + $repoName")

        getRepoIssues(repoOwner,repoName)
    }

      fun getRepoIssues(ownerName: String?, repoName: String?){
        getGitRepoIssuesUseCase(ownerName,repoName).onEach {result ->
            when(result){
                is Resources.Success -> {
                    println("result from vieuModel ${result.data}")
                    _gitRepoIssuesState.value =   GitRepoIssueState(repoIssue =result.data?: GitRepoIssuesDTO())
                }
                is Resources.Error -> {
                    _gitRepoIssuesState.value =  GitRepoIssueState(error = result.message?:"Unexpected Error Occurred")
                }

                is Resources.Loading ->{
                    _gitRepoIssuesState.value =   GitRepoIssueState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }






    class GitRepoIssuesViewModelFactory(
        private val getGitRepoIssuesUseCase: GetGitRepoIssuesUseCase,
        private val savedStateHandle: SavedStateHandle
    ):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(GitRepoIssuesViewModel::class.java)){
                GitRepoIssuesViewModel(getGitRepoIssuesUseCase,savedStateHandle)as T
            }else{
                throw IllegalArgumentException("GitRepoIssuesViewModel class is not found")
            }
        }
    }
}