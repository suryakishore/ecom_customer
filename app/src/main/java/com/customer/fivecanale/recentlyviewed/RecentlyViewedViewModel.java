package com.customer.fivecanale.recentlyviewed;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetRecentlyViewedProductsUseCase;
import com.customer.domain.model.recentlyviewed.RecentlyViewedData;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;

/*
 * Purpose – This class holds all the business logic of recent viewed activity.
 * @author 3Embed
 * Created on Jan 14, 2019
 * Modified on
 */
public class RecentlyViewedViewModel extends ViewModel {
  private UseCaseHandler mHandler;
  private GetRecentlyViewedProductsUseCase mViewedProductsUseCase;
  private MutableLiveData<RecentlyViewedData> mListDataMutableLiveData = new MutableLiveData<>();

  @Inject
  public RecentlyViewedViewModel(UseCaseHandler handler,
      GetRecentlyViewedProductsUseCase recentlyViewedProductsUseCase) {
    this.mHandler = handler;
    this.mViewedProductsUseCase = recentlyViewedProductsUseCase;
  }

  /**
   * This method is using to get all the recently viewed products from API
   */
  void getAllRecentlyViewedProducts() {
    DisposableSingleObserver<GetRecentlyViewedProductsUseCase.ResponseValues>
        disposableSingleObserver =
        new DisposableSingleObserver<GetRecentlyViewedProductsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetRecentlyViewedProductsUseCase.ResponseValues responseValues) {
            mListDataMutableLiveData.setValue(responseValues.getData());
          }

          @Override
          public void onError(Throwable e) {

          }
        };
    mHandler.execute(mViewedProductsUseCase,
        new GetRecentlyViewedProductsUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * This method is using to get recently viewed products mData emitter
   *
   * @return recently viewed products mData emitter
   */
  MutableLiveData<RecentlyViewedData> getListDataMutableLiveData() {
    return mListDataMutableLiveData;
  }
}