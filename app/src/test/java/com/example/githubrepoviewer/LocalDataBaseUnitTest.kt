package com.example.githubrepoviewer

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubrepoviewer.data.DummyRepoDetailItemDTO
import com.example.githubrepoviewer.data.localdatabase.GitRepoDataBase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class LocalDataBaseUnitTest {



    private var testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Test
    fun addToDataBase_isCorrectInserting() = testScope.runTest {
        val dummyItem = DummyRepoDetailItemDTO.getDummyRepoDetailsItem()

        database.insertGitRepo(dummyItem)
        val retrievedItem = database.retrieveGitRepo(dummyItem.id)
        assertEquals(dummyItem, retrievedItem)
    }

    @Test
    fun deleteDataFromDataBase_isCorrectRetrieving() = testScope.runTest {
        val dummyItem = DummyRepoDetailItemDTO.getDummyRepoDetailsItem()
        database.insertGitRepo(dummyItem)
        database.deleteGitRepo(dummyItem.id)


            val retrievedItem = database.retrieveGitRepo(dummyItem.id)
            assertNull(retrievedItem)

    }


   val database =  GitRepoDataBase.LocalGitRepoDataBase.createGitRepoDao(
       ApplicationProvider.getApplicationContext()
   )



}