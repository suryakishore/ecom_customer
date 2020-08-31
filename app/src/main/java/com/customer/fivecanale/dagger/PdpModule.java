package com.customer.fivecanale.dagger;

import com.customer.data.repository.AddProductToWishListRepositoryImpl;
import com.customer.data.repository.LikeDisLikeReviewRepositoryImpl;
import com.customer.data.repository.ProductDetailsRepositoryImpl;
import com.customer.data.repository.ReportReviewRepositoryImpl;
import com.customer.domain.interactor.AddProductToWishListUseCase;
import com.customer.domain.interactor.LikeDisLikeReviewUseCase;
import com.customer.domain.interactor.PdpUseCase;
import com.customer.domain.interactor.ReportReviewUseCase;
import com.customer.domain.interactor.UseCase;
import com.customer.domain.repository.AddProductToWishListRepository;
import com.customer.domain.repository.LikeDisLikeReviewRepository;
import com.customer.domain.repository.ProductDetailsRepository;
import com.customer.domain.repository.ReportReviewRepository;
import dagger.Module;
import dagger.Provides;

@Module
public class PdpModule {
  @Provides
  ProductDetailsRepository provideRepository(
      ProductDetailsRepositoryImpl productDetailsRepository) {
    return productDetailsRepository;
  }

  @Provides
  public UseCase<PdpUseCase.RequestValues, PdpUseCase.ResponseValues> getLoginDetailsUseCase(
      PdpUseCase pdpUseCase) {
    return pdpUseCase;
  }

  @Provides
  ReportReviewRepository provideReportReviewRepository(
      ReportReviewRepositoryImpl reportReviewRepository) {
    return reportReviewRepository;
  }

  @Provides
  UseCase<ReportReviewUseCase.RequestValues, ReportReviewUseCase.ResponseValues> getReportReviewUseCase(
      ReportReviewUseCase reportReviewUseCase) {
    return reportReviewUseCase;
  }

  @Provides
  LikeDisLikeReviewRepository provideLikeDislikeRepository(
      LikeDisLikeReviewRepositoryImpl reportReviewRepository) {
    return reportReviewRepository;
  }

  @Provides
  UseCase<LikeDisLikeReviewUseCase.RequestValues, LikeDisLikeReviewUseCase.ResponseValues> getLikeDisLikeUseCase(
      LikeDisLikeReviewUseCase likeDisLikeReviewUseCase) {
    return likeDisLikeReviewUseCase;
  }

  @Provides
  AddProductToWishListRepository provideLAddWishListRepository(
      AddProductToWishListRepositoryImpl addProductToWishListRepository) {
    return addProductToWishListRepository;
  }

  @Provides
  UseCase<AddProductToWishListUseCase.RequestValues, AddProductToWishListUseCase.ResponseValues> getAddWishListUseCase(
      AddProductToWishListUseCase addProductToWishListUseCase) {
    return addProductToWishListUseCase;
  }
}
