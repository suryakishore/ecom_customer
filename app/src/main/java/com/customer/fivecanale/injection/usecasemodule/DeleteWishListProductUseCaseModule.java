package com.customer.fivecanale.injection.usecasemodule;

import com.customer.data.repository.DeleteWishListProductRepositoryImpl;
import com.customer.domain.interactor.DeleteWishListProductUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.DeleteWishListProductRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class DeleteWishListProductUseCaseModule {

  @Provides
  DeleteWishListProductRepository provideRepository(
      DeleteWishListProductRepositoryImpl deleteWishListProductRepository) {
    return deleteWishListProductRepository;
  }

  @Provides
  public UseCase<DeleteWishListProductUseCase.RequestValues,
      DeleteWishListProductUseCase.ResponseValues> getUseCase(
      DeleteWishListProductUseCase deleteWishListProductUseCase) {
    return deleteWishListProductUseCase;
  }
}
