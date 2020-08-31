package com.customer.fivecanale.category;

import static com.customer.fivecanale.category.CategoryUiActions.BACK;
import static com.customer.fivecanale.category.CategoryUiActions.CART_COUNT;
import static com.customer.fivecanale.util.EcomConstants.ADD_CART;
import static com.customer.fivecanale.util.EcomConstants.DELETE_CART;
import static com.customer.fivecanale.util.EcomConstants.UPDATE_CART;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.ProductCategoryUseCase;
import com.customer.domain.interactor.handler.UserInfoHandler;
import com.customer.domain.model.productcategory.CategoryData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/*
 * Purpose – This class Holds all the Business logic of CategoryViewMoreActivity.
 * @author 3Embed
 * Created on Nov 10, 2019
 * Modified on
 */
public class CatViewMoreViewModel extends ViewModel implements BackButtonClickListener {
  @Inject
  public UserInfoHandler mUserInfoHandler;
  private ProductCategoryUseCase mCategoryUseCase;
  private UseCaseHandler mHandler;
  private MutableLiveData<CategoryData> mLiveData = new MutableLiveData<>();
  private MutableLiveData<CategoryUiActions> mUiActionsMutableLiveData = new MutableLiveData<>();
  private DisposableSingleObserver<ProductCategoryUseCase.ResponseValues> mDisposableSingleObserver;

  @Inject
  public CatViewMoreViewModel(ProductCategoryUseCase categoryUseCase,
      UseCaseHandler handler) {
    this.mCategoryUseCase = categoryUseCase;
    this.mHandler = handler;
  }

  /**
   * this method is using to get all the product category from API
   */
  private void callProductCategoryApi() {
    mDisposableSingleObserver =
        new DisposableSingleObserver<ProductCategoryUseCase.ResponseValues>() {
          @Override
          public void onSuccess(ProductCategoryUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("PRD_CAT_Page Success");
            mLiveData.postValue(responseValues.getData());
          }

          @Override
          public void onError(Throwable e) {
            EcomUtil.printLog("PRD_CAT_Page Fail" + e.getMessage());
          }
        };
  }

  /**
   * get the category or subcategory based on empty subcategory id.
   *
   * @param catId category id
   * @param from  from index.
   * @param to    to index.
   */
  void getCategoryData(String catId, int from, int to) {
    callProductCategoryApi();
    // if (TextUtils.isEmpty(subCatId)) {
    mHandler.execute(mCategoryUseCase, new ProductCategoryUseCase.RequestValues(catId, from, to),
        mDisposableSingleObserver);
    //}
    /*else {
      mHandler.execute(mCategoryUseCase,
          new ProductCategoryUseCase.RequestValues(catId, subCatId, from, to),
          mDisposableSingleObserver);
    }*/
  }

  /** This method is using for subscribing to User cart data */
  void onCartUpdate() {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    compositeDisposable.add(
        mUserInfoHandler
            .getCartDataObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(cartData -> {
              switch (cartData.getAction()) {
                case ADD_CART:
                  mUiActionsMutableLiveData.setValue(CART_COUNT);
                  break;
                case UPDATE_CART:
                  mUiActionsMutableLiveData.setValue(CART_COUNT);
                  break;
                case DELETE_CART:
                  mUiActionsMutableLiveData.setValue(CART_COUNT);
                  break;
                default:
                  break;
              }
            }));
  }

  /**
   * This method is using to get ProductCat live Data
   *
   * @return category mLiveData
   */
  MutableLiveData<CategoryData> getLiveData() {
    return mLiveData;
  }

  /**
   * This method is using to get Category page Click action listener live Data
   *
   * @return category click action live mData
   */
  MutableLiveData<CategoryUiActions> getClickActionLiveData() {
    return mUiActionsMutableLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mUiActionsMutableLiveData.setValue(BACK);
  }
}
