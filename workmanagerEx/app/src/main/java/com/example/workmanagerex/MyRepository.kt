package com.example.workmanagerex

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Owner(val login: String)
data class Repo(val name: String, val owner: Owner, val url: String)

@Entity
data class RepoD(
    @PrimaryKey val name: String,
    val owner: String
)

@Dao
interface MyDao {
    @Query("SELECT * FROM RepoD")
    fun getAll(): LiveData<List<RepoD>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repos: List<RepoD>)
}

@Database(entities = [RepoD::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract val myDao : MyDao

    companion object {
        private var INSTANCE: MyDatabase? = null
        fun getDatabase(context: Context) : MyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, MyDatabase::class.java, "repos_database")
                    .build()
            }
            return INSTANCE as MyDatabase
        }
    }
}

interface RestApi {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>
}

class MyRepository(context: Context) {
    private val baseURL = "https://api.github.com/"
    private val api = retrofitInit()


    private fun retrofitInit() : RestApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(RestApi::class.java)
    }

    private val myDao = MyDatabase.getDatabase(context).myDao
    val repos = myDao.getAll() // LiveData<List<ReposD>>

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            val repos = api.listRepos("test")
            // convert Repo to RepoD
            val repoDs = repos.map {
                RepoD(it.name, it.owner.login)
            }
            myDao.insertAll(repoDs)
        }
    }
}