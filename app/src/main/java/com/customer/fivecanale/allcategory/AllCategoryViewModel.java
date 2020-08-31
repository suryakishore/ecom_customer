package com.customer.fivecanale.allcategory;

import static com.customer.fivecanale.allcategory.AllCategoryUiActions.BACK;
import static com.customer.fivecanale.util.EcomConstants.FIVE;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.ProductCategoryUseCase;
import com.customer.domain.model.productcategory.CategoryData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;

/*
 * Purpose – This class Holds All the business logic of AllCategoryActivity.
 * @author 3Embed
 * Created on Nov 15, 2019
 * Modified on
 */
public class AllCategoryViewModel extends ViewModel implements BackButtonClickListener {
  private ProductCategoryUseCase mProductCategoryUseCase;
  private UseCaseHandler mCaseHandler;
  private MutableLiveData<CategoryData> mLiveData = new MutableLiveData<>();
  private MutableLiveData<AllCategoryUiActions> mActionsMutableLiveData = new MutableLiveData<>();
  private int mSize = FIVE;
  private int mSkip = ZERO;
  private boolean mCanLoad = true;
  private int mSubCatPenCount = FIVE;

  @Inject
  public AllCategoryViewModel(ProductCategoryUseCase productCategoryUseCase,
      UseCaseHandler handler) {
    this.mProductCategoryUseCase = productCategoryUseCase;
    this.mCaseHandler = handler;
  }

  /**
   * This method is using to get product categories list
   */
  void callProductCategoryApi() {
    DisposableSingleObserver<ProductCategoryUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ProductCategoryUseCase.ResponseValues>() {

          @Override
          public void onSuccess(ProductCategoryUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("PRD_CAT_Page Success");
            mLiveData.postValue(responseValues.getData());
            if (responseValues.getData() != null && !EcomUtil.isEmptyArray(
                responseValues.getData().getData())) {
              mSubCatPenCount = responseValues.getData().getData().get(ZERO).getPenCount();
            }
            mSkip = mSize;
            mSize += mSize;
            mCanLoad = true;

          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("PRD_CAT_Page Fail" + e.getMessage());
          }
        };

    if (mCanLoad && mSize <= mSubCatPenCount) {
      mCanLoad = false;
      mCaseHandler.execute(mProductCategoryUseCase,
          new ProductCategoryUseCase.RequestValues(mSkip, mSize),
          disposableSingleObserver);
    }

  }

  /**
   * This method is using to get API mData observable liveData
   *
   * @return category mData liveData
   */
  MutableLiveData<CategoryData> getLiveData() {
    return mLiveData;
  }

  /**
   * This method is using to get UI action click listener
   *
   * @return UI action Live Data
   */
  MutableLiveData<AllCategoryUiActions> getActionsMutableLiveData() {
    return mActionsMutableLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mActionsMutableLiveData.postValue(BACK);
  }
}
