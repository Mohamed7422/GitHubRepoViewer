package com.example.githubrepoviewer.ui.git_repo_issues_components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.githubrepoviewer.common.Constants
import com.example.githubrepoviewer.data.localdatabase.GitRepoDataBase
import com.example.githubrepoviewer.data.network.GitReposApiClient
import com.example.githubrepoviewer.data.repoImp.GitReposListRepoImpl
import com.example.githubrepoviewer.domain.use_case.GetGitRepoIssuesUseCase
import com.example.githubrepoviewer.ui.git_repo_issues_components.GitRepoIssuesViewModel.GitRepoIssuesViewModelFactory


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GitRepoIssuesScreen(
         navController: NavController,
         gitRepoIssuesUseCase: GetGitRepoIssuesUseCase = GetGitRepoIssuesUseCase(GitReposListRepoImpl(
             GitReposApiClient.gitReposApiClient,
             GitRepoDataBase.LocalGitRepoDataBase.createGitRepoDao(LocalContext.current)
         ))
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val savedStateHandle = navBackStackEntry?.savedStateHandle ?: SavedStateHandle()

    val ownerName = navController.currentBackStackEntry?.arguments?.getString(Constants.GIT_REPO_OWNER)
    val repoName = navController.currentBackStackEntry?.arguments?.getString(Constants.GIT_REPO_NAME)

    savedStateHandle[Constants.GIT_REPO_OWNER] =ownerName
    savedStateHandle[Constants.GIT_REPO_NAME] =repoName

    val viewModelFactory = GitRepoIssuesViewModelFactory(gitRepoIssuesUseCase,savedStateHandle)
    val viewModel:GitRepoIssuesViewModel = viewModel(factory = viewModelFactory)
    val gitRepoIssueState = viewModel.gitRepoIssueState.value
    Log.i("RepoIssueScreen","git repo Issues ${gitRepoIssueState.repoIssue.size}")

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp)){
             items(gitRepoIssueState.repoIssue){gitRepoIssue->
                 GitRepoIssueItem(gitRepoIssueItem = gitRepoIssue)

             }
        }



        if (gitRepoIssueState.repoIssue.isEmpty()){
            Row (modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                Icon(imageVector = Icons.Default.Close, contentDescription = "Stars" )
                Text(text = "NO ISSUE AVAILABLE" , color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)


            }
        }

        if (gitRepoIssueState.error.isNotBlank()){
            Text(
                text = gitRepoIssueState.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (gitRepoIssueState.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }
}




