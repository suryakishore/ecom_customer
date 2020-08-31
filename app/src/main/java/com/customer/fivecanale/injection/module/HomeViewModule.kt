package com.customer.fivecanale.injection.module

import com.customer.data.repository.HomePageRepositoryImpl
import com.customer.data.repository.HomeSubCategoryRepositoryImpl
import com.customer.domain.interactor.HomePageUseCase
import com.customer.domain.interactor.HomeSubCatUseCase
import com.customer.domain.interactor.UseCase
import com.customer.domain.repository.HomePageRepository
import com.customer.domain.repository.HomeSubCategoryRepository
import dagger.Module
import dagger.Provides

@Module
class HomeViewModule {
    @Provides
    fun provideHomeRepository(homePageRepositoryImpl: HomePageRepositoryImpl): HomePageRepository =
            homePageRepositoryImpl

    @Provides
    fun getHomeUseCase(homePageUseCase: HomePageUseCase): UseCase<HomePageUseCase.RequestValues, HomePageUseCase.ResponseValues> =
            homePageUseCase

    @Provides
    fun provideHomeSubCatRepository(homeSubCatRepositoryImpl: HomeSubCategoryRepositoryImpl): HomeSubCategoryRepository =
            homeSubCatRepositoryImpl

    @Provides
    fun getHomeSubCatUseCase(homeSubCatPageUseCase: HomeSubCatUseCase): UseCase<HomeSubCatUseCase.RequestValues, HomeSubCatUseCase.ResponseValues> =
            homeSubCatPageUseCase
}