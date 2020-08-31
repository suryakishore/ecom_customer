package com.customer.fivecanale.injection

import android.content.Context
import com.customer.data.DataSource
import com.customer.data.DataSourceImpl
import com.customer.data.executor.JobExecutor
import com.customer.delivX.dagger.ApplicationScoped
import com.customer.domain.UseCaseHandler
import com.customer.domain.executor.PostExecutionThread
import com.customer.domain.executor.ThreadExecutor
import com.customer.fivecanale.UiThread
import dagger.Module
import dagger.Provides

@Module
class CleanArchAppModule {
    @Provides
    @ApplicationScoped
    fun provideDataSource(context: Context): DataSource {
        return DataSourceImpl.getInstance(context)
    }

    @ApplicationScoped
    @Provides
    fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @ApplicationScoped
    @Provides
    fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @ApplicationScoped
    @Provides
    fun provideUseCaseHandler(): UseCaseHandler {
        return UseCaseHandler.getInstance()
    }
}