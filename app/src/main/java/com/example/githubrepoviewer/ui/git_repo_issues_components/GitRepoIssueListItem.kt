package com.example.githubrepoviewer.ui.git_repo_issues_components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.githubrepoviewer.data.network.dto.repo_issues_dto.GitRepoIssuesDTOItem
import com.example.githubrepoviewer.ui.theme.ColorPrimary2
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GitRepoIssueItem(
    gitRepoIssueItem: GitRepoIssuesDTOItem,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors()
           ) {
        Column ( modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)){

            val issueIcon = Icons.Default.Build
            RowForRepoDetail(label ="Issue Title:",value = gitRepoIssueItem.title,issueIcon)
            Spacer(modifier = Modifier.height(2.dp))

            val authorIcon = Icons.Default.AccountCircle
            RowForRepoDetail("Author Name:",gitRepoIssueItem.user.login,authorIcon)
            Spacer(modifier = Modifier.height(2.dp))

            val dateIcon = Icons.Outlined.DateRange
            RowForRepoDetailDate("Date :",gitRepoIssueItem,dateIcon)
            Spacer(modifier = Modifier.height(2.dp))

            val lockIcon = Icons.Default.Lock
            RowForRepoDetailState("State : ",gitRepoIssueItem.state,lockIcon)
            Spacer(modifier = Modifier.height(2.dp))
        }

    }

  }

@Composable
private fun RowForRepoDetail(label: String,value: String,icon:ImageVector) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )
    }
}
@Composable
private fun RowForRepoDetailState(label: String, value: String, lockIcon: ImageVector) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(imageVector = lockIcon, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = value,
            color = ColorPrimary2,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun RowForRepoDetailDate(
    label: String,
    gitRepoIssueItem: GitRepoIssuesDTOItem,
    dateIcon: ImageVector
) {

    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(imageVector = dateIcon, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "${getDateUTCFormatted(gitRepoIssueItem.createdAt)}",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
//this function for converting from  ISO 8601 format ex: ("2023-12-18T10:08:19Z")
private fun getDateUTCFormatted(dateStringUTC: String): String? {
    val format:String?
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    format = try {
        val instant = Instant.parse(dateStringUTC)
        val toLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        dateTimeFormatter.format(toLocalDate)
    }catch (e: DateTimeParseException){
        dateStringUTC
    }
    return format
}
