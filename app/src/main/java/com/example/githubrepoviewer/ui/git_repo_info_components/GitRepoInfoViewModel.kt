package com.example.githubrepoviewer.ui.git_repo_info_components

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubrepoviewer.common.Constants
import com.example.githubrepoviewer.common.Resources
import com.example.githubrepoviewer.domain.use_case.GetGitRepoInfoUseCase
import com.example.githubrepoviewer.domain.use_case.GetRepoInfoFromDataBaseUseCase
import com.example.githubrepoviewer.domain.use_case.IGetGitRepoInfoUseCase
import com.example.githubrepoviewer.domain.use_case.IGetRepoInfoFromDataBaseUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("SuspiciousIndentation")
class GitRepoInfoViewModel(
    private val gitRepoInfoUseCase: IGetGitRepoInfoUseCase,
    private val gitRepoInfoFromDataBaseUseCase: IGetRepoInfoFromDataBaseUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel() {

      private var _gitRepoInfoItemState  = mutableStateOf(GitRepoInfoState())
      val gitRepoInfoItemState:State<GitRepoInfoState> = _gitRepoInfoItemState

     init {
         val repoOwner = savedStateHandle.get<String>(Constants.GIT_REPO_OWNER)
         val repoName = savedStateHandle.get<String>(Constants.GIT_REPO_NAME)
         val repoId = savedStateHandle.get<Int>(Constants.GIT_REPO_ID)

                getRepoInfoFromDataBase(repoId){hasData->
                    if (!hasData){
                        getGitRepoInfo(repoOwner,repoName)
                    }
                }
     }
            fun getGitRepoInfo(owner: String?, repoName:String?){

                  gitRepoInfoUseCase.getGitRepoItem(owner,repoName).onEach {result->
                      when(result){

                          is Resources.Success -> {
                              _gitRepoInfoItemState.value = GitRepoInfoState(repoDetailItemDTO = result.data)
                              println("result state data From Remote ${result.data}")

                          }

                          is Resources.Error -> {
                              _gitRepoInfoItemState.value = GitRepoInfoState(error = result.message?:
                              "Unexpected Error Occurred")
                          }

                          is Resources.Loading -> {
                              _gitRepoInfoItemState.value = GitRepoInfoState(isLoading = true)
                          }

                      }

                  }.launchIn(viewModelScope)


      }
            fun getRepoInfoFromDataBase(owner: Int?,onResult:(Boolean)->Unit){

                  gitRepoInfoFromDataBaseUseCase.getGitRepoItemFromDataBase(owner).onEach {result->
                      when(result){

                          is Resources.Success -> {
                              _gitRepoInfoItemState.value = GitRepoInfoState(repoDetailItemDTO = result.data)
                              println("result state data From DataBase ${result.data}")
                              onResult(true)


                          }

                          is Resources.Error -> {
                              _gitRepoInfoItemState.value = GitRepoInfoState(error = result.message?:
                              "Unexpected Error Occurred")
                              onResult(false)
                          }

                          is Resources.Loading -> {
                              _gitRepoInfoItemState.value = GitRepoInfoState(isLoading = true)
                          }

                      }

                  }.launchIn(viewModelScope)


      }

    class GitRepoInfoViewModelFactory(private val useCase: GetGitRepoInfoUseCase,
                                      private val getRepoInfoFromDataBaseUseCase: GetRepoInfoFromDataBaseUseCase,
        private val savedStateHandle: SavedStateHandle):ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(GitRepoInfoViewModel::class.java)){
                   GitRepoInfoViewModel(useCase,getRepoInfoFromDataBaseUseCase,savedStateHandle)as T
            }else{
                throw IllegalArgumentException("GitRepoInfoViewModel class not found")
            }
        }
    }


}


