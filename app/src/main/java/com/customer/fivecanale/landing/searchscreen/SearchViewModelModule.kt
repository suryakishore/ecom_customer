package com.customer.fivecanale.landing.searchscreen

import androidx.lifecycle.ViewModel
import com.customer.fivecanale.injection.ViewModelFactory
import com.customer.fivecanale.injection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchViewModelModule : ViewModelFactory() {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    protected abstract fun searchViewModel(searchViewModel: SearchViewModel): ViewModel
}