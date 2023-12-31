package com.example.githubrepoviewer.data.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO


@Dao
interface GitRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGitRepo(repoDetailItemDTO: RepoDetailItemDTO)

    @Query("SELECT * FROM repository WHERE id = :repoId")
    suspend fun retrieveGitRepo(repoId:Int?): RepoDetailItemDTO

    @Query("SELECT * FROM repository WHERE owner = :ownerName")
    suspend fun retrieveGitRepoByOwnerName(ownerName: String?): RepoDetailItemDTO

    @Update
    suspend fun updateGitRepo(repoDetailItemDTO: RepoDetailItemDTO)

    @Query("DELETE FROM repository WHERE id = :id")
    suspend fun deleteGitRepo(id:Int?)
}