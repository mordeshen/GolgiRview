package com.e.golgirview.di.main

import com.e.golgirview.api.ApiService
import com.e.golgirview.persistence.AppDatabase
import com.e.golgirview.persistence.MainDao
import com.e.golgirview.repo.MainRepository
import com.e.golgirview.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }


    @MainScope
    @Provides
    fun provideMainRepository(
        apiService: ApiService,
        mainDao: MainDao,
        sessionManager: SessionManager
    ): MainRepository {
        return MainRepository(apiService,mainDao ,sessionManager)
    }

    @MainScope
    @Provides
    fun provideMainDao(db: AppDatabase): MainDao {
        return db.getMainDao()
    }

}