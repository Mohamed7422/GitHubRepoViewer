package com.example.githubrepoviewer.data.localdatabase


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.OwnerTypeConvertor
import com.example.githubrepoviewer.data.network.dto.git_repos_dto.RepoDetailItemDTO


@Database(entities = [RepoDetailItemDTO::class], version = 1, exportSchema = false)
@TypeConverters(OwnerTypeConvertor::class)
abstract class GitRepoDataBase:RoomDatabase() {

    abstract fun gitRepoDao():GitRepoDao

    object LocalGitRepoDataBase{
        fun createGitRepoDao(context: Context):GitRepoDao{
            return Room.databaseBuilder(context.applicationContext,
                GitRepoDataBase::class.java,"gitRepoDataBase.dp").build().gitRepoDao()
        }
    }
}