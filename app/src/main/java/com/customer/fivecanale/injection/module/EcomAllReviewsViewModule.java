package com.customer.fivecanale.injection.module;

import com.customer.data.repository.GetAllReviewsRepositoryImpl;
import com.customer.domain.interactor.GetAllReviewsUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.GetAllReviewsRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class EcomAllReviewsViewModule {
  @Provides
  GetAllReviewsRepository provideRepository(GetAllReviewsRepositoryImpl signInRepository) {
    return signInRepository;
  }

  @Provides
  public UseCase<GetAllReviewsUseCase.RequestValues, GetAllReviewsUseCase.ResponseValues> getLoginDetailsUseCase(
      GetAllReviewsUseCase signInUseCase) {
    return signInUseCase;
  }
}
