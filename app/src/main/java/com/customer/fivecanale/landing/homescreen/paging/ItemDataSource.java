package com.customer.fivecanale.landing.homescreen.paging;

import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.HomeSubCatUseCase;
import com.customer.domain.model.homesubcategory.HomeSubCategoryData;
import io.reactivex.observers.DisposableSingleObserver;

public class ItemDataSource extends PageKeyedDataSource<Integer, HomeSubCategoryData> {
  private static final int PAGE_SIZE = FIVE;
  private static final int FIRST_PAGE = ZERO;
  private UseCaseHandler mHandler;
  private HomeSubCatUseCase mProductsUseCase;

  public void setDataSource(UseCaseHandler handler, HomeSubCatUseCase useCase) {
    this.mHandler = handler;
    this.mProductsUseCase = useCase;
  }

  @Override
  public void loadInitial(@NonNull LoadInitialParams<Integer> params,
      @NonNull LoadInitialCallback<Integer, HomeSubCategoryData> callback) {
  }

  @Override
  public void loadBefore(@NonNull LoadParams<Integer> params,
      @NonNull LoadCallback<Integer, HomeSubCategoryData> callback) {
    DisposableSingleObserver<HomeSubCatUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<HomeSubCatUseCase.ResponseValues>() {
          @Override
          public void onSuccess(HomeSubCatUseCase.ResponseValues responseValues) {
          }

          @Override
          public void onError(Throwable e) {
          }
        };
    mHandler.execute(mProductsUseCase, new HomeSubCatUseCase.RequestValues(PAGE_SIZE, FIRST_PAGE),
        disposableSingleObserver);
  }

  @Override
  public void loadAfter(@NonNull LoadParams<Integer> params,
      @NonNull LoadCallback<Integer, HomeSubCategoryData> callback) {
    DisposableSingleObserver<HomeSubCatUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<HomeSubCatUseCase.ResponseValues>() {
          @Override
          public void onSuccess(HomeSubCatUseCase.ResponseValues responseValues) {
          }

          @Override
          public void onError(Throwable e) {
          }
        };
    mHandler.execute(mProductsUseCase,
        new HomeSubCatUseCase.RequestValues(PAGE_SIZE + PAGE_SIZE, PAGE_SIZE),
        disposableSingleObserver);
  }
}