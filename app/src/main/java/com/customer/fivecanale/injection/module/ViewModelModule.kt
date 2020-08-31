package com.customer.fivecanale.injection.module

import androidx.lifecycle.ViewModel
import com.customer.fivecanale.injection.ViewModelFactory
import com.customer.fivecanale.injection.ViewModelKey
import com.customer.fivecanale.landing.HomeModel
import com.customer.fivecanale.landing.cartscreen.CartViewModel
import com.customer.fivecanale.landing.historyscreen.HistoryViewModel
import com.customer.fivecanale.landing.homescreen.HomeViewModel
import com.customer.fivecanale.landing.profile.ProfileViewModel
import com.customer.fivecanale.landing.searchscreen.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule : ViewModelFactory() {
    /*
      * This method basically says
      * inject this object into a Map using the @IntoMap annotation,
      * with the  LoginViewModel.class as key,
      * and a Provider that will build a LoginViewModel
      * object.
      *
      * */
    @Binds
    @IntoMap
    @ViewModelKey(HomeModel::class)
    protected abstract fun homeActViewModel(homeModel: HomeModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    protected abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    protected abstract fun searchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    protected abstract fun profileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    protected abstract fun cartViewModel(cartViewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    protected abstract fun historyViewModel(historyViewModel: HistoryViewModel): ViewModel
}