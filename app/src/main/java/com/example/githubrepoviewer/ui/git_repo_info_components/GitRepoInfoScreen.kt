package com.example.githubrepoviewer.ui.git_repo_info_components

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.githubrepoviewer.common.Constants
import com.example.githubrepoviewer.data.localdatabase.GitRepoDataBase
import com.example.githubrepoviewer.data.network.GitReposApiClient
import com.example.githubrepoviewer.data.repoImp.GitReposListRepoImpl
import com.example.githubrepoviewer.domain.use_case.GetGitRepoInfoUseCase
import com.example.githubrepoviewer.domain.use_case.GetRepoInfoFromDataBaseUseCase
import com.example.githubrepoviewer.ui.Screen
import com.example.githubrepoviewer.ui.git_repo_info_components.GitRepoInfoViewModel.GitRepoInfoViewModelFactory
import com.example.githubrepoviewer.ui.theme.Gold


@Composable
fun GitRepoInfoScreen(
     navController: NavController,
     gitRepoInfoUseCase: GetGitRepoInfoUseCase = GetGitRepoInfoUseCase(
         GitReposListRepoImpl.getInstance(
             GitReposApiClient.gitReposApiClient,
             GitRepoDataBase.LocalGitRepoDataBase.createGitRepoDao(LocalContext.current)
         )
     ),
     getRepoInfoFromDataBaseUseCase: GetRepoInfoFromDataBaseUseCase  = GetRepoInfoFromDataBaseUseCase(
         GitReposListRepoImpl.getInstance(
             GitReposApiClient.gitReposApiClient,
             GitRepoDataBase.LocalGitRepoDataBase.createGitRepoDao(LocalContext.current)
         )
     )
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val savedStateHandle  = navBackStackEntry?.savedStateHandle ?: SavedStateHandle()

    val ownerName = navController.currentBackStackEntry?.arguments?.getString(Constants.GIT_REPO_OWNER)
    val repoName = navController.currentBackStackEntry?.arguments?.getString(Constants.GIT_REPO_NAME)
    val repoId= navController.currentBackStackEntry?.arguments?.getString(Constants.GIT_REPO_ID)

    savedStateHandle[Constants.GIT_REPO_OWNER] = ownerName
    savedStateHandle[Constants.GIT_REPO_NAME] = repoName
    savedStateHandle[Constants.GIT_REPO_ID] = repoId?.toInt()

    Log.i("GitRepoInfoScreen"," gitRepoItem string $ownerName $repoName ${repoId?.toInt()}")

    val viewModelFactory = GitRepoInfoViewModelFactory(
     gitRepoInfoUseCase,getRepoInfoFromDataBaseUseCase, savedStateHandle
    )


    val viewModel:GitRepoInfoViewModel = viewModel(factory = viewModelFactory)

    val gitRepoInfoState = viewModel.gitRepoInfoItemState.value
    Log.i("GitScreen","state data ${gitRepoInfoState.repoDetailItemDTO}")



    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)){

          gitRepoInfoState.repoDetailItemDTO?.let { gitRepoItem->
             LazyColumn(modifier = Modifier.fillMaxSize() ,

                  ) {
                 item {
                     RepositoryDetailCard(label = "Repo Name", value = gitRepoItem.name)
                     RepositoryDetailCard(label = "Owner", value = gitRepoItem.owner.login?:"No Name Found")
                     RepositoryDetailCard(label = "Description", value = gitRepoItem.description?:"No Description Found")


                     Card {
                         Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                             Icon(imageVector = Icons.Default.Star, contentDescription = "Stars", tint = Gold)
                             Text(
                                 text = "Stars Count: ",
                                 style = MaterialTheme.typography.bodyMedium,
                                 modifier = Modifier.padding(start = 4.dp)
                             )
                             Text(
                                 text = "${gitRepoItem.stargazersCount}",
                                 style = MaterialTheme.typography.bodyMedium,
                                 modifier = Modifier.padding(start = 4.dp)
                             )
                         }
                     }

                     RepositoryDetailCard(label = "Open Issues", value = gitRepoItem.openIssues.toString())
                     RepositoryDetailCard(label = "Subscribers", value = gitRepoItem.subscribersCount.toString())
                     RepositoryDetailCard(label = "Watchers", value = gitRepoItem.watchersCount.toString())



                     Box(
                         contentAlignment = Alignment.Center,
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(top = 16.dp)
                     ) {
                         Button( onClick = {
                             navController.navigate(Screen.GitRepoIssuesScreen.route+"/${gitRepoItem.owner.login}"+"/${gitRepoItem.name}")
                         }) {
                             Text(text = "VIEW ISSUES", color = if (isSystemInDarkTheme())Color.Black else Color.White )
                         }
                     }
                 }
             }





         }


        if (gitRepoInfoState.error.isNotBlank()){
            Text(
                text = gitRepoInfoState.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (gitRepoInfoState.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        
    }



}

@Composable
@Preview
fun RepositoryDetailsScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        BoxWithConstraints() {
            val scrollState = rememberScrollState()
            val viewMaxHeight = maxHeight.value
            val columnMaxScroll = scrollState.maxValue
            val scrollStateValue = scrollState.value
            val paddingSize = (scrollStateValue * viewMaxHeight) / columnMaxScroll
            val animation = animateDpAsState(targetValue = paddingSize.dp)
            val scrollBarHeight = viewMaxHeight / 20

            Column(
                Modifier
                    .verticalScroll(state = scrollState)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (scrollStateValue < columnMaxScroll) {
                    Box(
                        modifier = Modifier
                            .paddingFromBaseline(animation.value)
                            .padding(all = 4.dp)
                            .height(scrollBarHeight.dp)
                            .width(4.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = DefaultAlpha),
                                shape = MaterialTheme.shapes.medium
                            )
//                            .align(Alignment.TopEnd)
                    ) {

                    }
                }
            }
        }
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            item {
//                RepositoryDetailCard(label = "Repo Name", value = "repoName")
//                RepositoryDetailCard(label = "Owner", value = "repoOwner")
//                RepositoryDetailCard(label = "Description", value = "description")
//
//                 Card {
//                     Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
//                         Icon(imageVector = Icons.Default.Star, contentDescription = "Stars", tint = Gold)
//                         Text(
//                             text = "Stars Count: ",
//                             style = MaterialTheme.typography.bodyMedium,
//                             modifier = Modifier.padding(start = 4.dp)
//                         )
//                         Text(
//                             text = "50",
//                             style = MaterialTheme.typography.bodyMedium,
//                             modifier = Modifier.padding(start = 4.dp)
//                         )
//                     }
//                 }
//
//
//                RepositoryDetailCard(label = "Open Issues", value = "605")
//                RepositoryDetailCard(label = "Subscribers", value = "50")
//                RepositoryDetailCard(label = "Watchers", value = "60")
//
//                Box(modifier = Modifier.fillMaxWidth(),
//                    contentAlignment = Alignment.Center ){
//                    Button(
//                        onClick = { /* TODO */ },
//                        modifier = Modifier
//                            .padding(top = 16.dp)
//                    ) {
//                        Text(text = "View Issues")
//                    }
//                }
//
//            }
//        }
    }
}

@Composable
fun RepositoryDetailCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            if (!value.isNullOrEmpty()){
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium
                )
            }else{
                Text(
                    text = "No Value Found",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}

