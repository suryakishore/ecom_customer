package com.customer.fivecanale.landing.searchscreen;

import static com.customer.fivecanale.util.EcomConstants.FALSE;
import static com.customer.fivecanale.util.EcomConstants.TRUE;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.GetRecentSearchSuggestionUseCase;
import com.customer.domain.interactor.GetSuggestionUseCase;
import com.customer.domain.interactor.ProductCategoryUseCase;
import com.customer.domain.interactor.ProductCategoryUseCase.ResponseValues;
import com.customer.domain.model.productcategory.CategoryData;
import com.customer.domain.model.recentsearch.RecentSearchSuggestionData;
import com.customer.domain.model.suggestion.SuggestionItemData;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import javax.inject.Inject;

/*
 * Purpose – This class Holds all the Business Logic for SearchActivity.
 * @author 3Embed
 * Created on Nov 10, 2019
 * Modified on
 */
public class SearchViewModel extends ViewModel {
  public ObservableField<Boolean> progressVisible = new ObservableField<>(TRUE);
  private ProductCategoryUseCase mCategoryUseCase;
  private UseCaseHandler mCaseHandler;
  private GetRecentSearchSuggestionUseCase mRecentSearchSuggestionUseCase;
  private MutableLiveData<CategoryData> mLiveData = new MutableLiveData<>();
  private MutableLiveData<Boolean> mEmptyScreen = new MutableLiveData<>();
  private MutableLiveData<ArrayList<SuggestionItemData>> mSuggestionLiveData =
      new MutableLiveData<>();
  private GetSuggestionUseCase mSuggestionUseCase;
  private MutableLiveData<Boolean> mUiVisibility = new MutableLiveData<>();
  private MutableLiveData<ArrayList<RecentSearchSuggestionData>> mRecentSugLiveData =
      new MutableLiveData<>();

  @Inject
  public SearchViewModel(ProductCategoryUseCase categoryUseCase,
      GetSuggestionUseCase suggestionUseCase,
      GetRecentSearchSuggestionUseCase recentSearchSuggestionUseCase,
      UseCaseHandler handler) {
    this.mCategoryUseCase = categoryUseCase;
    this.mRecentSearchSuggestionUseCase = recentSearchSuggestionUseCase;
    this.mCaseHandler = handler;
    this.mSuggestionUseCase = suggestionUseCase;
  }

  /**
   * This method is using to get product category list from API
   */
  void callProductCategoryApi() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<ProductCategoryUseCase.ResponseValues>() {
          @Override
          public void onSuccess(ProductCategoryUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("PRD_CAT_Page Success");
            progressVisible.set(FALSE);
            mLiveData.postValue(responseValues.getData());
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            EcomUtil.printLog("PRD_CAT_Page Fail" + e.getMessage());
          }
        };
    mCaseHandler.execute(mCategoryUseCase, new ProductCategoryUseCase.RequestValues(0, 10),
        disposableSingleObserver);
  }

  /**
   * This method is using to get Category mData listener
   *
   * @return category emiitable mutable live mData
   */
  MutableLiveData<CategoryData> getLiveData() {
    return mLiveData;
  }

  /**
   * This method is using to get SuggestionList emitter Live Data
   *
   * @return SuggestionLIst LiveData to observe
   */
  MutableLiveData<ArrayList<SuggestionItemData>> getSuggestionLiveData() {
    return mSuggestionLiveData;
  }

  /**
   * This method is using to get Suggestions from APi
   *
   * @param searchItem search text to get suggestion
   */
  void getSuggestions(String searchItem) {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetSuggestionUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<GetSuggestionUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetSuggestionUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("SuggestionList Success");
            progressVisible.set(FALSE);
            if (responseValues.getData() != null && !EcomUtil.isEmptyArray(
                responseValues.getData().getData())) {
              mSuggestionLiveData.setValue(responseValues.getData().getData());
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
            mSuggestionLiveData.setValue(new ArrayList<>());
            mEmptyScreen.setValue(true);
            EcomUtil.printLog("SuggestionList Fail" + e.getMessage());
          }
        };
    mCaseHandler.execute(mSuggestionUseCase, new GetSuggestionUseCase.RequestValues(searchItem),
        disposableSingleObserver);
  }

  /**
   * This method is using to get recent search suggestion list
   */
  void getRecentSuggestions() {
    progressVisible.set(TRUE);
    DisposableSingleObserver<GetRecentSearchSuggestionUseCase.ResponseValues>
        disposableSingleObserver =
        new DisposableSingleObserver<GetRecentSearchSuggestionUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetRecentSearchSuggestionUseCase.ResponseValues responseValues) {
            progressVisible.set(FALSE);
            if (!EcomUtil.isEmptyArray(responseValues.getData().getData())) {
              mRecentSugLiveData.postValue(responseValues.getData().getData());
            }
          }

          @Override
          public void onError(Throwable e) {
            progressVisible.set(FALSE);
          }
        };
    mCaseHandler.execute(mRecentSearchSuggestionUseCase,
        new GetRecentSearchSuggestionUseCase.RequestValues(),
        disposableSingleObserver);
  }

  /**
   * This method is using to get EmptyScreen Observable
   *
   * @return boolean mutable live mData
   */
  MutableLiveData<Boolean> getEmptyScreen() {
    return mEmptyScreen;
  }

  /**
   * This method is using to handle UI
   *
   * @return boolean value to handle Empty screen
   */
  MutableLiveData<Boolean> getUiVisibility() {
    return mUiVisibility;
  }

  /**
   * This method is using to get recent search live data
   *
   * @return recent search result live data
   */
  MutableLiveData<ArrayList<RecentSearchSuggestionData>> getRecentSugLiveData() {
    return mRecentSugLiveData;
  }
}