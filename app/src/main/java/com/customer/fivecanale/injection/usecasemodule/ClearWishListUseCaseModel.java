package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.ClearWishListRepositoryImpl;
import com.customer.domain.interactor.ClearWishListUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.ClearWishListRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ClearWishListUseCaseModel {
  @Provides
  ClearWishListRepository provideRepository(ClearWishListRepositoryImpl clearWishListRepository) {
    return clearWishListRepository;
  }

  @Provides
  public UseCase<ClearWishListUseCase.RequestValues, ClearWishListUseCase.ResponseValues> getLoginDetailsUseCase(
      ClearWishListUseCase clearWishListUseCase) {
    return clearWishListUseCase;
  }
}
