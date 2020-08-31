package com.customer.fivecanale.filter;

import static com.customer.fivecanale.util.EcomConstants.BRAND_LEVEL;
import static com.customer.fivecanale.util.EcomConstants.CAT_LEVEL;
import static com.customer.fivecanale.util.EcomConstants.SUB_CAT_LEVEL;
import static com.customer.fivecanale.util.EcomConstants.SUB_SUB_CAT_LEVEL;
import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.customer.domain.UseCaseHandler;
import com.customer.domain.interactor.FilteredProductsUseCase;
import com.customer.domain.interactor.GetFilterParamsUseCase;
import com.customer.domain.interactor.GetFilterParamsUseCase.ResponseValues;
import com.customer.domain.model.filter.FilterList;
import com.customer.domain.model.filter.FilterListData;
import com.customer.fivecanale.util.BackButtonClickListener;
import com.customer.fivecanale.util.EcomUtil;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;

/*
 * Purpose – This class Holds all the logic of ProductFilterActivity and acts as an interactor
 * with other layers.
 * @author 3Embed
 * Created on Dec 05, 2019
 * Modified on
 */
public class ProductFilterViewModel extends ViewModel implements BackButtonClickListener {
  public ObservableField<Boolean> isProgressVisible;
  private FilteredProductsUseCase mFilteredProductsUseCase;
  private GetFilterParamsUseCase mGetFilterParamsUseCase;
  private UseCaseHandler mCaseHandler;
  private MutableLiveData<ArrayList<FilterList>> mFilterParams = new MutableLiveData<>();
  private DisposableSingleObserver<ResponseValues> mDisposableSingleObserver;
  private MutableLiveData<Integer> mLiveData = new MutableLiveData<>();
  private HashMap<Integer, Set<String>> mPrevSelectedMap;
  private MutableLiveData<FilterUiActon> mUiLiveData = new MutableLiveData<>();
  private ArrayList<FilterList> filterData;

  @Inject
  public ProductFilterViewModel(GetFilterParamsUseCase getFilterParamsUseCase,
      FilteredProductsUseCase filteredProductsUseCase,
      UseCaseHandler handler) {
    isProgressVisible = new ObservableField<>(false);
    this.mFilteredProductsUseCase = filteredProductsUseCase;
    this.mGetFilterParamsUseCase = getFilterParamsUseCase;
    this.mCaseHandler = handler;
  }

  /**
   * Setting the Previously selected Filter Values
   *
   * @param filterMap previously selected Filter values
   */
  void setPreviousFilerParam(HashMap<Integer, Set<String>> filterMap) {
    this.mPrevSelectedMap = filterMap;
  }

  /**
   * this method is using to get the Filter params from the API
   *
   * @param catName       category name to get filter param under this category
   * @param subCatName    subCatName name to get filter param under this category
   * @param subSubCatName subSubCatName name to get filter param under this category
   */
  void getFilterParams(String catName, String subCatName, String subSubCatName,
      String searchQuery, String brandName) {
    if (!TextUtils.isEmpty(catName) && !TextUtils.isEmpty(subCatName) && !TextUtils.isEmpty(
        subSubCatName)) {
      EcomUtil.printLog("exe" + "catName" + catName + "subCatName" + subCatName + "subSubCatName"
          + subSubCatName);
      mCaseHandler.execute(mGetFilterParamsUseCase,
          new GetFilterParamsUseCase.RequestValues(SUB_SUB_CAT_LEVEL, catName, subCatName,
              subSubCatName),
          mDisposableSingleObserver);
    } else if (!TextUtils.isEmpty(brandName)) {
      mCaseHandler.execute(mGetFilterParamsUseCase,
          new GetFilterParamsUseCase.RequestValues(brandName, BRAND_LEVEL),
          mDisposableSingleObserver);
    } else if (!TextUtils.isEmpty(catName) && !TextUtils.isEmpty(subCatName)) {
      mCaseHandler.execute(mGetFilterParamsUseCase,
          new GetFilterParamsUseCase.RequestValues(SUB_CAT_LEVEL, catName, subCatName),
          mDisposableSingleObserver);
    } else if (!TextUtils.isEmpty(catName)) {
      mCaseHandler.execute(mGetFilterParamsUseCase,
          new GetFilterParamsUseCase.RequestValues(CAT_LEVEL, catName),
          mDisposableSingleObserver);
    } else if (!TextUtils.isEmpty(searchQuery)) {
      mCaseHandler.execute(mGetFilterParamsUseCase,
          new GetFilterParamsUseCase.RequestValues(searchQuery),
          mDisposableSingleObserver);
    }
  }

  /**
   * Calling API to get Filter Parameter and publishing it to UI
   */
  void getFilterParam() {
    mDisposableSingleObserver =
        new DisposableSingleObserver<GetFilterParamsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(GetFilterParamsUseCase.ResponseValues responseValues) {
            EcomUtil.printLog("FilterParam Success");
            isProgressVisible.set(false);
            filterData = responseValues.getData().getFilters();

            /*Check whether both Filter list from API and previously selected Filter list are
             not null */
            if (filterData != null && filterData.size() > ZERO && mPrevSelectedMap != null
                && mPrevSelectedMap.size() > ZERO) {

              /*Iterating through previously selected Filter list*/
              for (Map.Entry<Integer, Set<String>> entry : mPrevSelectedMap.entrySet()) {
                FilterList selectedList = new FilterList(entry.getKey());

                /*Checking the previously selected FilterType is present in new API list and
                previously selected Filter has more than one selected value*/
                if (filterData.contains(selectedList) && entry.getValue() != null
                    && entry.getValue().size() > ZERO) {
                  int index = filterData.indexOf(selectedList);
                  for (String subValue : entry.getValue()) {
                    FilterListData data = new FilterListData(subValue);

                    /*Checking previously selected Filter value present in new filter type and if
                     it present setting it to previously selected to true*/
                    if (filterData.get(index).getData().contains(data)) {
                      int subIndex = filterData.get(index).getData().indexOf(data);
                      filterData.get(index).getData().get(subIndex).setSelected(true);
                    }
                  }
                }
              }
            }
            mFilterParams.postValue(filterData);
            mDisposableSingleObserver.dispose();
          }

          @Override
          public void onError(Throwable e) {
            isProgressVisible.set(false);
            mDisposableSingleObserver.dispose();
            EcomUtil.printLog("FilterParam Fail" + e.getMessage());
          }
        };
  }

  /**
   * Getter for Filter Param LiveData
   *
   * @return Filter param LiveData
   */
  MutableLiveData<ArrayList<FilterList>> getFilterParamLiveData() {
    return mFilterParams;
  }

  /**
   * Getter for Filter Product LiveData
   *
   * @return filtered product LiveData
   */
  MutableLiveData<Integer> getProductListLiveData() {
    return mLiveData;
  }

  /**
   * Making API call to get all the Filtered products
   *
   * @param filterMap filter values to get Products
   */
  void getFilteredProducts(HashMap<Integer, Set<String>> filterMap) {
    isProgressVisible.set(true);
    DisposableSingleObserver<FilteredProductsUseCase.ResponseValues> disposableSingleObserver =
        new DisposableSingleObserver<FilteredProductsUseCase.ResponseValues>() {
          @Override
          public void onSuccess(FilteredProductsUseCase.ResponseValues responseValues) {
            isProgressVisible.set(false);
            if (responseValues.getData().getProducts() != null
                && responseValues.getData().getProducts().size() > ZERO) {
              mLiveData.postValue(responseValues.getData().getPenCount());
            }
          }

          @Override
          public void onError(Throwable e) {
            isProgressVisible.set(false);
            EcomUtil.printLog("FilterAPI Fail" + e.getMessage());
          }
        };
  }

  /**
   * Returns the user profile mData clieck event live mData object
   *
   * @return live mData object
   */
  MutableLiveData<FilterUiActon> getFilterUiActionLiveData() {
    return mUiLiveData;
  }

  @Override
  public void backButtonClickListener(View view) {
    mUiLiveData.setValue(FilterUiActon.BACK_BUTTON);
  }

  /**
   * This method is using to clear filter applied to the List
   */
  void clearFilter() {
    if (!EcomUtil.isEmptyArray(filterData)) {
      for (int i = ZERO; i < filterData.size(); i++) {
        if (!EcomUtil.isEmptyArray(filterData.get(i).getData())) {
          for (int j = ZERO; j < filterData.get(i).getData().size(); j++) {
            filterData.get(i).getData().get(j).setSelected(false);
          }
        }
      }
      mFilterParams.postValue(filterData);
    }
  }
}
