package com.example.wtest.lib.repo

import android.content.Context
import androidx.room.Room
import com.example.wtest.lib.repo.git.Git
import com.example.wtest.lib.repo.git.GitAPI
import com.example.wtest.lib.repo.room.LocalSQL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CookieJar
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import org.apache.commons.csv.CSVFormat
import retrofit2.Retrofit
import java.net.CookieManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    //region vars
    private const val LOCAL_SQL_NAME = "LocalSQL.db"
    private const val GITHUB_URL = "https://github.com/"
    //endregion vars

    /**
     * Provides a [CookieJar] for [OkHttpClient.Builder]
     */
    @Singleton
    @Provides
    fun providesCookieJar(): CookieJar = JavaNetCookieJar(CookieManager())

    /**
     * Provides a [OkHttpClient] to [Retrofit.Builder]
     */
    @Singleton
    @Provides
    fun providesOkHttpClient(cookieJar: CookieJar): OkHttpClient {
        return OkHttpClient
            .Builder()
            .cookieJar(cookieJar)
            .build()
    }

    /**
     * Create and provide the git API implementation using a [OkHttpClient] and both git's URL and API interface
     */
    @Singleton
    @Provides
    fun providesGitAPI(okHttpClient: OkHttpClient): GitAPI {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(GITHUB_URL)
            .build()
            .create(GitAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesGit(api: GitAPI): Git = Git(api)

    @Singleton
    @Provides
    fun providesRoom(@ApplicationContext context: Context): LocalSQL {
        return Room
            .databaseBuilder(context, LocalSQL::class.java, LOCAL_SQL_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides a custom [CSVFormat] to translate
     */
    @Singleton
    @Provides
    fun providesCSVFormat(): CSVFormat {
        return CSVFormat
            .DEFAULT
            .builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .build()
    }

    @Singleton
    @Provides
    fun providesRepo(csvFormat: CSVFormat, git: Git, localSQL: LocalSQL): Repo = DefaultRepo(csvFormat, git, localSQL)
}