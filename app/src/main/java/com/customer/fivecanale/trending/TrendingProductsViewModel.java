package com.customer.fivecanale.trending;

import static com.customer.fivecanale.trending.TrendingUiEvent.BACK;
import static com.customer.fivecanale.util.EcomConstants.ONE;
import static com.customer.fivecanale.util.EcomConstants.TEN;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetOfferProductsUseCase;
import com.customer.domain.model.homesubcategory.HomeSubCategoryData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import javax.inject.Inject;

/*
 * Purpose – This class Holds All the Logic of Today deals Offer products.
 * @author 3Embed
 * Created on Dec 25, 2019
 * Modified on
 */
public class TrendingProductsViewModel extends ViewModel implements BackButtonClickListener {
  private GetOfferProductsUseCase mProductsUseCase;
  private UseCaseHandler mHandler;
  private MutableLiveData<ArrayList<HomeSubCategoryData>> mProductsMutableLiveData =
      new MutableLiveData<>();
  private MutableLiveData<TrendingUiEvent> mEventMutableLiveData = new MutableLiveData<>();

  @Inject
  public TrendingProductsViewModel(UseCaseHandler handler,
      GetOfferProductsUseCase productsUseCase) {
    this.mHandler = handler;
    this.mProductsUseCase = productsUseCase;
  }

  /**
   * This method is using to get all the Today deals offer products
   */
  void getTodayDeals() {
    DisposableSingleObserver<GetOfferProductsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetOfferProductsUseCase.ResponseValues>() {

          @Override
          public void onSuccess(GetOfferProductsUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("TodayOfferData Success");
            if (responseValues.getData() != null && !EcomUtil.isEmptyArray(
                responseValues.getData().getData())) {
              mProductsMutableLiveData.postValue(responseValues.getData().getData());
            }
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("TodayOfferData Fail" + e.getMessage());
          }
        };
    mHandler.execute(mProductsUseCase, new GetOfferProductsUseCase.RequestValues(ONE, TEN),
        disposableSingleObserver);
  }

  /**
   * This method is using to get Products listing mutable live mData
   *
   * @return products list mutable live mData
   */
  MutableLiveData<ArrayList<HomeSubCategoryData>> getProductsLiveData() {
    return mProductsMutableLiveData;
  }


  /**
   * This method is using to get Products listing mutable live mData
   *
   * @return products list mutable live mData
   */
  MutableLiveData<TrendingUiEvent> getEventMutableLiveData() {
    return mEventMutableLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mEventMutableLiveData.postValue(BACK);
  }
}