package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.GetWishListRepositoryImpl;
import com.customer.domain.interactor.GetWishListUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetWishListRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class GetWishListProductsUseCaseModule {

  @Provides
  GetWishListRepository provideRepository(GetWishListRepositoryImpl getWishListRepository) {
    return getWishListRepository;
  }

  @Provides
  public UseCase<GetWishListUseCase.RequestValues, GetWishListUseCase.ResponseValues> getLoginDetailsUseCase(
      GetWishListUseCase getWishListUseCase) {
    return getWishListUseCase;
  }
}
