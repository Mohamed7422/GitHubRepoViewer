package com.example.githubrepoviewer.ui.git_repos_components.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.GitReposDTOItem


@Composable
fun GitReposListItem(
    gitRepoItem: GitReposDTOItem,
    onItemCLick:(GitReposDTOItem)->Unit
) {

    Card( modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemCLick(gitRepoItem) }
        .padding(10.dp),
      elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors()
    ) {
        Column(
            modifier =  Modifier.padding(10.dp)
        ) {

            Row (verticalAlignment = Alignment.CenterVertically){

                Icon(imageVector = Icons.Default.List,contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Repo name:",
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${gitRepoItem.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row (horizontalArrangement = Arrangement.SpaceBetween){
                Icon(imageVector = Icons.Default.Person,contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Owner:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.primary,

                    )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "${gitRepoItem.owner?.login}",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row (horizontalArrangement = Arrangement.SpaceBetween){
                Icon(imageVector = Icons.Default.Info,contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Description:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(2.dp))
                if (!gitRepoItem.description.isNullOrEmpty()){
                    Text(
                        text = "${gitRepoItem.description}",
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis
                    )

                }else{
                    Text(
                        text = "No description available",
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }

        }

    }


    }
