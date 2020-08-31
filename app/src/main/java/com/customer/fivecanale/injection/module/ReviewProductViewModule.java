package com.customer.fivecanale.injection.module;

import com.customer.data.repository.GetRatableAttributesRepositoryImpl;
import com.customer.data.repository.ProductRateAndReviewRepositoryImpl;
import com.customer.domain.interactor.GetRatableAttributesUseCase;
import com.customer.domain.interactor.ProductRateAndReviewUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetRatableAttributesRepository;
import com.customer.domain.repository.ProductRateAndReviewRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class ReviewProductViewModule {
  @Provides
  GetRatableAttributesRepository provideRepository(
      GetRatableAttributesRepositoryImpl getRatableAttributesRepository) {
    return getRatableAttributesRepository;
  }

  @Provides
  public UseCase<GetRatableAttributesUseCase.RequestValues,
      GetRatableAttributesUseCase.ResponseValues> getAttributesData(
      GetRatableAttributesUseCase pdpUseCase) {
    return pdpUseCase;
  }

  @Provides
  ProductRateAndReviewRepository provideReviewRepository(
      ProductRateAndReviewRepositoryImpl getRatableAttributesRepository) {
    return getRatableAttributesRepository;
  }

  @Provides
  public UseCase<ProductRateAndReviewUseCase.RequestValues,
      ProductRateAndReviewUseCase.ResponseValues> postReviewData(
      ProductRateAndReviewUseCase pdpUseCase) {
    return pdpUseCase;
  }
}
