package com.example.githubrepoviewer.data

import com.example.githubrepoviewer.data.network.dto.git_repos_dto.Owner
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO

object DummyRepoDetailItemDTO {

   fun getDummyRepoDetailsItem()= RepoDetailItemDTO(
    1,"defaultBranch","dummyDescription",5,6,"DummyName","english",
    "FullRepoName",1,"node",5,6, dummyOwner,5,6,6,
    "visible",5,5
   )

 val dummyOwner = Owner(
  // Assume the Owner class has these fields. Replace them with actual fields and dummy values.
  login = "dummy1Login",
  id = 124,
  node_id = "dummyNodeId",
  avatar_url = "https://dummyavatarurl.com",
  gravatar_id = "",
  url = "https://dummyurl.com",
  html_url = "https://dummyhtmlurl.com",
  followers_url = "https://dummyfollowersurl.com",
  following_url = "https://dummyfollowingurl.com",
  gists_url = "https://dummygistsurl.com",
  starred_url = "https://dummystarredurl.com",
  subscriptions_url = "https://dummysubscriptionsurl.com",
  organizations_url = "https://dummyorganizationsurl.com",
  repos_url = "https://dummyreposurl.com",
  events_url = "https://dummyeventsurl.com",
  received_events_url = "https://dummyreceivedeventsurl.com",
  type = "User",
  site_admin = false
 )

}